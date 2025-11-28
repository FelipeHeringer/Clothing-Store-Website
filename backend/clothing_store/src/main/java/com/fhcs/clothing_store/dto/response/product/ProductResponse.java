package com.fhcs.clothing_store.dto.response.product;

import com.fhcs.clothing_store.entity.product.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    
    private boolean success;
    private String message;
    private Product product;

    public static ProductResponse success(Product product, String message) {
        return ProductResponse.builder()
                .success(true)
                .message(message)
                .product(product)
                .build();
    }

    public static ProductResponse error(String message) {
        return ProductResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static ProductResponse messageOnly(boolean success, String message) {
        return ProductResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
