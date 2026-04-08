package com.fhcs.clothing_store.dto.response.cart;
 
import java.math.BigDecimal;
import java.util.List;
 
import com.fhcs.clothing_store.entity.cart.Cart;
 
import lombok.Builder;
import lombok.Getter;
 
@Builder
@Getter
public class CartDto {
 
    private Integer cartId;
    private String status;
    private List<CartItemDto> items;
    private BigDecimal total;
    private Integer itemCount;
 
    public static CartDto fromCart(Cart cart) {
        List<CartItemDto> itemDtos = cart.getItems().stream()
                .map(CartItemDto::fromCartItem)
                .toList();
 
        BigDecimal total = itemDtos.stream()
                .map(CartItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
 
        return CartDto.builder()
                .cartId(cart.getCartId())
                .status(cart.getStatus().name())
                .items(itemDtos)
                .total(total)
                .itemCount(itemDtos.size())
                .build();
    }
}