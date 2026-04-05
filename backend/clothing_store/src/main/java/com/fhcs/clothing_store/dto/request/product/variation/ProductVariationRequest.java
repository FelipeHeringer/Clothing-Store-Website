package com.fhcs.clothing_store.dto.request.product.variation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductVariationRequest {

    @NotNull(message = "A cor é obrigatorio")
    private Integer colorId;

    @NotNull(message = "O tamanho é obrigatorio")
    private Integer sizeId;

    private String skuCode;

    // private String imageUrl;
}
