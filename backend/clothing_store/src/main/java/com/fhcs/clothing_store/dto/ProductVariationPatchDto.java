package com.fhcs.clothing_store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationPatchDto {
    
    private Integer colorId;

    private Integer sizeId;

    private String skuCode;

    private String imageUrl;
}
