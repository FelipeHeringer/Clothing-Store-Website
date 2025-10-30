package com.fhcs.clothing_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrivateIndividualPatchDto {

    @NotBlank(message = "O nome é obrigatório")
    private String individualName;

    @NotBlank(message = "O número de telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "O número de telefone deve estar no formato (XX) XXXXX-XXXX")
    private String phoneNumber;
    
}
