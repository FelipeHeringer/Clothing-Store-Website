package com.fhcs.clothing_store.dto.request;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PrivateIndividualRequest {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email invalido")
    @Size(max = 255, message = "Email muito longo")
    private String email;

    @NotBlank(message = "O nome é obrigatório")
    private String individualName;

    @NotBlank(message = "A data de nascimento é obrigatória")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "A data de nascimento deve estar no formato DD-MM-AAAA")
    @Past(message = "A data de nascimento deve estar no passado")
    private String birthDate;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O número de telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "O número de telefone deve estar no formato (XX) XXXXX-XXXX")
    private String phoneNumber;

    @Valid
    @NotNull(message = "O endereço é obrigatório")
    private AddressRequest address;
}
