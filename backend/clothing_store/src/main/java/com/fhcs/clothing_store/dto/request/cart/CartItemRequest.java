package com.fhcs.clothing_store.dto.request.cart;
 
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
 
@Getter
public class CartItemRequest {
 
    @NotNull(message = "O id da variação é obrigatório")
    private Integer variationId;
 
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima é 1")
    private Integer quantity;
}