
package com.fhcs.clothing_store.controller.admin;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
import com.fhcs.clothing_store.dto.response.order.OrderResponse;
import com.fhcs.clothing_store.service.OrderService;
 
@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminOrderController {
 
    @Autowired private OrderService orderService;
 
    @PatchMapping("/{orderId}/confirm")
    public ResponseEntity<OrderResponse> confirm(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.confirmOrder(orderId), "Pedido confirmado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error(e.getMessage()));
        }
    }
 
    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponse> markAsPaid(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.markAsPaid(orderId), "Pedido marcado como pago."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error(e.getMessage()));
        }
    }
 
    @PatchMapping("/{orderId}/ship")
    public ResponseEntity<OrderResponse> markAsShipped(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.markAsShipped(orderId), "Pedido enviado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error(e.getMessage()));
        }
    }
 
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponse> markAsDelivered(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok(OrderResponse.success(
                    orderService.markAsDelivered(orderId), "Pedido entregue."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(OrderResponse.error(e.getMessage()));
        }
    }
}