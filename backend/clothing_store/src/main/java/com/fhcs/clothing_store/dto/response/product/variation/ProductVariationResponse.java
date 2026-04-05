package com.fhcs.clothing_store.dto.response.product.variation;

import com.fhcs.clothing_store.entity.product.Product;
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductVariationResponse {
    private boolean success;
    private String message;
    private ProductVariation productVariation;
    private Product product;

    public static ProductVariationResponse success(Product product, ProductVariation productVariation, String message) {
        return ProductVariationResponse.builder()
                .success(true)
                .message(message)
                .product(product)
                .productVariation(productVariation)
                .build();
    }

    public static ProductVariationResponse error(String message) {
        return ProductVariationResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static ProductVariationResponse messageOnly(boolean success, String message) {
        return ProductVariationResponse.builder()
                .success(success)
                .message(message)
                .build();
    }

}
