package com.fhcs.clothing_store.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fhcs.clothing_store.dto.request.cart.CartItemRequest;
import com.fhcs.clothing_store.dto.response.cart.CartDto;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.cart.Cart;
import com.fhcs.clothing_store.entity.cart.CartItem;
import com.fhcs.clothing_store.entity.cart.CartStatus;
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;
import com.fhcs.clothing_store.repository.cart.CartItemRepository;
import com.fhcs.clothing_store.repository.cart.CartRepository;
import com.fhcs.clothing_store.repository.product.variation.ProductVariationRepository;

@Service
@Transactional
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductVariationRepository variationRepository;
    @Autowired private PrivateIndividualService individualService;

    public CartDto getOrCreateCart(String accessToken) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Cart cart = findOrCreateActiveCart(individual);
        return CartDto.fromCart(cart);
    }

    public CartDto addItem(String accessToken, CartItemRequest request) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Cart cart = findOrCreateActiveCart(individual);

        ProductVariation variation = variationRepository.findById(request.getVariationId())
                .orElseThrow(() -> new IllegalArgumentException("Variação não encontrada."));

        validateStock(variation, request.getQuantity());

        cartItemRepository.findByCart_CartIdAndVariation_VariationId(
                cart.getCartId(), variation.getVariationId())
                .ifPresentOrElse(
                        existing -> {
                            int newQty = existing.getQuantity() + request.getQuantity();
                            validateStock(variation, newQty);
                            existing.setQuantity(newQty);
                            cartItemRepository.save(existing);
                        },
                        () -> {
                            CartItem item = CartItem.builder()
                                    .cart(cart)
                                    .variation(variation)
                                    .quantity(request.getQuantity())
                                    .unitPrice(variation.getProduct().getPrice())
                                    .build();
                            cart.getItems().add(item);
                        });

        cart.setUpdatedAt(Instant.now());
        cartRepository.save(cart);

        Cart refreshed = cartRepository.findById(cart.getCartId()).orElseThrow();
        return CartDto.fromCart(refreshed);
    }

    public CartDto updateItemQuantity(String accessToken, Integer cartItemId, Integer quantity) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Cart cart = getActiveCartOrThrow(individual);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        assertItemBelongsToCart(item, cart);

        if (quantity <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            validateStock(item.getVariation(), quantity);
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        cart.setUpdatedAt(Instant.now());
        cartRepository.save(cart);

        Cart refreshed = cartRepository.findById(cart.getCartId()).orElseThrow();
        return CartDto.fromCart(refreshed);
    }

    public CartDto removeItem(String accessToken, Integer cartItemId) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Cart cart = getActiveCartOrThrow(individual);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        assertItemBelongsToCart(item, cart);
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        cart.setUpdatedAt(Instant.now());
        cartRepository.save(cart);

        Cart refreshed = cartRepository.findById(cart.getCartId()).orElseThrow();
        return CartDto.fromCart(refreshed);
    }

    public CartDto clearCart(String accessToken) {
        PrivateIndividual individual = individualService.getPrivateIndividualByToken(accessToken);
        Cart cart = getActiveCartOrThrow(individual);
        cart.getItems().clear();
        cart.setUpdatedAt(Instant.now());
        cartRepository.save(cart);
        return CartDto.fromCart(cart);
    }

    // Called by OrderService during checkout — marks cart as CHECKED_OUT
    public Cart checkoutCart(PrivateIndividual individual) {
        Cart cart = getActiveCartOrThrow(individual);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("O carrinho está vazio.");
        }
        cart.setStatus(CartStatus.CHECKED_OUT);
        cart.setUpdatedAt(Instant.now());
        return cartRepository.save(cart);
    }

    public Cart findOrCreateActiveCart(PrivateIndividual individual) {
        return cartRepository
                .findByIndividual_IndividualIdAndStatus(individual.getIndividualId(), CartStatus.ACTIVE)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .individual(individual)
                        .build()));
    }

    private Cart getActiveCartOrThrow(PrivateIndividual individual) {
        return cartRepository
                .findByIndividual_IndividualIdAndStatus(individual.getIndividualId(), CartStatus.ACTIVE)
                .orElseThrow(() -> new IllegalStateException("Nenhum carrinho ativo encontrado."));
    }

    private void validateStock(ProductVariation variation, int requested) {
        if (!variation.hasStock(requested)) {
            throw new IllegalArgumentException(
                    String.format("Estoque insuficiente para '%s'. Disponível: %d.",
                            variation.getSkuCode(), variation.getStock()));
        }
    }

    private void assertItemBelongsToCart(CartItem item, Cart cart) {
        if (!item.getCart().getCartId().equals(cart.getCartId())) {
            throw new IllegalArgumentException("Este item não pertence ao seu carrinho.");
        }
    }
}