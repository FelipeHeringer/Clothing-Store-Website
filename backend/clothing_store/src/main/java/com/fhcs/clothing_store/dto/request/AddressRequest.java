package com.fhcs.clothing_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AddressRequest {


    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O CEP deve estar no formato XXXXX-XXX")
    private String cep;
    
    @NotBlank(message = "A cidade é obrigatória")
    private String city;

    @NotBlank(message = "O estado é obrigatório")
    private String stateUF;

    @NotBlank(message = "O nome da rua é obrigatório")
    private String streetName;

    @NotNull(message = "O número é obrigatório")
    private Integer number;

    private String complement;

    private String description = "Casa"; 
}
