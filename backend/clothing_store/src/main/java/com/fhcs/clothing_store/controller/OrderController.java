package com.fhcs.clothing_store.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.fhcs.clothing_store.dto.response.order.OrderDto;
import com.fhcs.clothing_store.dto.response.order.OrderResponse;
import com.fhcs.clothing_store.service.OrderService;
 
@RestController
@RequestMapping("/api/orders")
public class OrderController {
 
    @Autowired private OrderService orderService;
 
    @GetMapping
    public PagedModel<OrderDto> getMyOrders(
            @RequestHeader("Authorization") String token,
            @PageableDefault(size = 10) Pageable pageable) {
        String accessToken = token.substring(7);
        return orderService.getMyOrders(accessToken, pageable);
    }
 
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getMyOrderById(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer orderId) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.getMyOrderById(accessToken, orderId),
                    "Pedido recuperado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error("Erro ao recuperar pedido: " + e.getMessage()));
        }
    }
 
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer orderId) {
        try {
            String accessToken = token.substring(7);
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.cancelOrder(accessToken, orderId), "Pedido cancelado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error("Erro ao cancelar pedido: " + e.getMessage()));
        }
    }
}