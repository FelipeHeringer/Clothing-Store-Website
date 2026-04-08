package com.fhcs.clothing_store.dto.response.cart;
 
import java.math.BigDecimal;
 
import com.fhcs.clothing_store.entity.cart.CartItem;
 
import lombok.Builder;
import lombok.Getter;
 
@Builder
@Getter
public class CartItemDto {
 
    private Integer cartItemId;
    private Integer variationId;
    private String productName;
    private String color;
    private String size;
    private String skuCode;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private Integer availableStock;
 
    public static CartItemDto fromCartItem(CartItem item) {
        BigDecimal sub = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return CartItemDto.builder()
                .cartItemId(item.getCartItemId())
                .variationId(item.getVariation().getVariationId())
                .productName(item.getVariation().getProduct().getName())
                .color(item.getVariation().getColor().getColor())
                .size(item.getVariation().getSize().getSize())
                .skuCode(item.getVariation().getSkuCode())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(sub)
                .availableStock(item.getVariation().getStock())
                .build();
    }
}
