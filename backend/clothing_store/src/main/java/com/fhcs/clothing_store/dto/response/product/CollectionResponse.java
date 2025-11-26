package com.fhcs.clothing_store.dto.response.product;

import java.util.List;

import com.fhcs.clothing_store.entity.product.Collection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CollectionResponse {

    private boolean success;
    private String message;
    private List<Collection> collections;

    public static CollectionResponse success(List<Collection> collections, String message) {
        return CollectionResponse.builder()
                .success(true)
                .message(message)
                .collections(collections)
                .build();
    }

    public static CollectionResponse error(String message) {
        return CollectionResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static CollectionResponse messageOnly(boolean success, String message) {
        return CollectionResponse.builder()
                .success(success)
                .message(message)
                .build();
    }

}
