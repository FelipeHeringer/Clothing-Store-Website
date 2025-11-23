package com.fhcs.clothing_store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPatch {
    
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String categoryName;
}
