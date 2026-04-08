package com.fhcs.clothing_store.controller;
 
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.fhcs.clothing_store.dto.request.cart.CartItemRequest;
import com.fhcs.clothing_store.dto.request.cart.CheckoutRequest;
import com.fhcs.clothing_store.dto.response.cart.CartResponse;
import com.fhcs.clothing_store.dto.response.order.OrderDto;
import com.fhcs.clothing_store.dto.response.order.OrderResponse;
import com.fhcs.clothing_store.service.CartService;
import com.fhcs.clothing_store.service.OrderService;
 
@RestController
@RequestMapping("/api/cart")
public class CartController {
 
    @Autowired private CartService cartService;
    @Autowired private OrderService orderService;
 
    @GetMapping
    public ResponseEntity<CartResponse> getOrCreateCart(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(CartResponse.success(
                    cartService.getOrCreateCart(accessToken), "Carrinho recuperado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CartResponse.error("Erro ao recuperar carrinho: " + e.getMessage()));
        }
    }
 
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CartItemRequest request) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.status(HttpStatus.CREATED).body(CartResponse.success(
                    cartService.addItem(accessToken, request), "Item adicionado ao carrinho."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CartResponse.error("Erro ao adicionar item: " + e.getMessage()));
        }
    }
 
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer cartItemId,
            @RequestParam Integer quantity) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(CartResponse.success(
                    cartService.updateItemQuantity(accessToken, cartItemId, quantity),
                    "Item atualizado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CartResponse.error("Erro ao atualizar item: " + e.getMessage()));
        }
    }
 
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeItem(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer cartItemId) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(CartResponse.success(
                    cartService.removeItem(accessToken, cartItemId), "Item removido do carrinho."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CartResponse.error("Erro ao remover item: " + e.getMessage()));
        }
    }
 
    @DeleteMapping
    public ResponseEntity<CartResponse> clearCart(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(CartResponse.success(
                    cartService.clearCart(accessToken), "Carrinho esvaziado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CartResponse.error("Erro ao limpar carrinho: " + e.getMessage()));
        }
    }
 
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CheckoutRequest request) {
        try {
            String accessToken = token.substring(7);
            OrderDto order = orderService.checkout(accessToken, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(OrderResponse.success(order, "Pedido criado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error("Erro ao finalizar compra: " + e.getMessage()));
        }
    }
}