package com.fhcs.clothing_store.dto.request.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 150, message = "O nome do produto não pode exceder 150 caracteres")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 255, message = "A descrição do produto não pode exceder 255 caracteres")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que zero") 
    private BigDecimal price;

    @NotNull(message = "A categoria do produto é obrigatória")
    private Integer categoryId;

    @NotNull(message = "A coleção do produto é obrigatória")
    private Integer collectionId;

    private Integer score = 0;
}
