package com.fhcs.clothing_store.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchDto {
    
    private String name;
    private String description;
    private BigDecimal price;
    private Integer categoryId;
    private Integer collectionId;
    private Integer score;
}
