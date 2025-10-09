package com.fhcs.clothing_store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {

    @Size(min = 3, max = 50, message = "O nome deve possuir no mínimo 3 caracteres e no máximo 50")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "O nome do usuário pode conter somente letras, números e underline")
    private String username;

    @Email(message = "Formato de email invalido")
    @Size(max = 255, message = "Email muito longo")
    private String email;

    @Size(min = 8, max = 128, message = "A senha deve possuir pelo menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;'\"|,.<>/?~`-]).+$", message = "A senha deve conter pelo menos uma letra maiúscula,um digito e um caracter especial")
    private String passwordHash;
}
