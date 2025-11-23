package com.fhcs.clothing_store.dto.response.product;

import java.util.List;

import com.fhcs.clothing_store.entity.product.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryResponse {

    private boolean success;
    private String message;
    private List<Category> categories;

    public static CategoryResponse success(List<Category> categories, String message) {
        return CategoryResponse.builder()
                .success(true)
                .message(message)
                .categories(categories)
                .build();
    }

    public static CategoryResponse error(String message) {
        return CategoryResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static CategoryResponse messageOnly(boolean success, String message) {
        return CategoryResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
