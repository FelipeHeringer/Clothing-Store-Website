package com.fhcs.clothing_store.service;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fhcs.clothing_store.dto.request.cart.CheckoutRequest;
import com.fhcs.clothing_store.dto.response.order.OrderDto;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.cart.Cart;
import com.fhcs.clothing_store.entity.cart.CartItem;
import com.fhcs.clothing_store.entity.order.Order;
import com.fhcs.clothing_store.entity.order.OrderItem;
import com.fhcs.clothing_store.entity.order.OrderStatus;
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;
import com.fhcs.clothing_store.repository.order.OrderRepository;
import com.fhcs.clothing_store.repository.product.variation.ProductVariationRepository;

@Service
@Transactional
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductVariationRepository variationRepository;
    @Autowired private CartService cartService;
    @Autowired private PrivateIndividualService individualService;

    // -----------------------------------------------------------------
    //  Checkout: converts the active cart into a PENDING order
    // -----------------------------------------------------------------
    public OrderDto checkout(String accessToken, CheckoutRequest request) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);

        // Lock and mark cart as checked out
        Cart cart = cartService.checkoutCart(individual);

        // Re-validate stock atomically before committing
        for (CartItem cartItem : cart.getItems()) {
            ProductVariation variation = cartItem.getVariation();
            if (!variation.hasStock(cartItem.getQuantity())) {
                throw new IllegalStateException(
                        String.format("Estoque insuficiente para '%s'. Disponível: %d.",
                                variation.getSkuCode(), variation.getStock()));
            }
        }

        // Build order
        Order order = Order.builder()
                .individual(individual)
                .paymentMethod(request.getPaymentMethod())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            ProductVariation variation = cartItem.getVariation();
            BigDecimal subtotal = cartItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .variation(variation)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getUnitPrice())
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(orderItem);
            total = total.add(subtotal);

            // Deduct stock
            variation.setStock(variation.getStock() - cartItem.getQuantity());
            variationRepository.save(variation);
        }

        order.setTotal(total);
        Order saved = orderRepository.save(order);

        return OrderDto.fromOrder(saved);
    }

    // -----------------------------------------------------------------
    //  Status transitions
    // -----------------------------------------------------------------
    public OrderDto confirmOrder(Integer orderId) {
        return transitionStatus(orderId, OrderStatus.PENDING, OrderStatus.CONFIRMED);
    }

    public OrderDto markAsPaid(Integer orderId) {
        return transitionStatus(orderId, OrderStatus.CONFIRMED, OrderStatus.PAID);
    }

    public OrderDto markAsShipped(Integer orderId) {
        return transitionStatus(orderId, OrderStatus.PAID, OrderStatus.SHIPPED);
    }

    public OrderDto markAsDelivered(Integer orderId) {
        return transitionStatus(orderId, OrderStatus.SHIPPED, OrderStatus.DELIVERED);
    }

    public OrderDto cancelOrder(String accessToken, Integer orderId) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);

        Order order = getOrderOrThrow(orderId);
        assertOrderBelongsToIndividual(order, individual);

        if (order.getStatus() == OrderStatus.SHIPPED
                || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Pedido não pode ser cancelado no status: " + order.getStatus());
        }

        // Restore stock if order was already confirmed/paid
        if (order.getStatus() == OrderStatus.CONFIRMED || order.getStatus() == OrderStatus.PAID) {
            restoreStock(order);
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(Instant.now());
        return OrderDto.fromOrder(orderRepository.save(order));
    }

    // -----------------------------------------------------------------
    //  Queries
    // -----------------------------------------------------------------
    public PagedModel<OrderDto> getMyOrders(String accessToken, Pageable pageable) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Page<OrderDto> page = orderRepository
                .findByIndividual_IndividualId(individual.getIndividualId(), pageable)
                .map(OrderDto::fromOrder);
        return new PagedModel<>(page);
    }

    public OrderDto getMyOrderById(String accessToken, Integer orderId) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Order order = getOrderOrThrow(orderId);
        assertOrderBelongsToIndividual(order, individual);
        return OrderDto.fromOrder(order);
    }

    // -----------------------------------------------------------------
    //  Helpers
    // -----------------------------------------------------------------
    private OrderDto transitionStatus(Integer orderId, OrderStatus expected, OrderStatus next) {
        Order order = getOrderOrThrow(orderId);
        if (order.getStatus() != expected) {
            throw new IllegalStateException(
                    String.format("Status inválido para transição. Esperado: %s, Atual: %s",
                            expected, order.getStatus()));
        }
        order.setStatus(next);
        order.setUpdatedAt(Instant.now());
        return OrderDto.fromOrder(orderRepository.save(order));
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            ProductVariation variation = item.getVariation();
            variation.setStock(variation.getStock() + item.getQuantity());
            variationRepository.save(variation);
        }
    }

    private Order getOrderOrThrow(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + orderId));
    }

    private void assertOrderBelongsToIndividual(Order order, PrivateIndividual individual) {
        if (!order.getIndividual().getIndividualId().equals(individual.getIndividualId())) {
            throw new IllegalArgumentException("Pedido não pertence ao usuário autenticado.");
        }
    }
}