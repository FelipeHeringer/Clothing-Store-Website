package com.fhcs.clothing_store.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CollectionRequest {

    @NotBlank(message = "O nome da coleção é obrigatório")
    @Size(min =3, max = 150, message = "O nome da coleção deve possuir no mínimo 3 caracteres e no máximo 150")
    private String collectionName;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição não deve exceder 255 caracteres")
    private String description;

    @NotBlank(message = "A data de lançamento é obrigatória")
    private String launchDate;
    
}
