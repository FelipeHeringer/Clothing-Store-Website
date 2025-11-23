package com.fhcs.clothing_store.dto.request.product;

import javax.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryRequest {

    @NotBlank(message = "O nome da categoria é obrigátorio.")
    @Size(min = 3, max = 150, message = "O nome da categoria deve possuir no mínimo 3 caracteres e no máximo 150")
    private String categoryName;
    
}
