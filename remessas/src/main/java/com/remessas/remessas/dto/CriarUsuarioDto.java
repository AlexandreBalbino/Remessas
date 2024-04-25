package com.remessas.remessas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriarUsuarioDto {

    @NotBlank(message = "Name is required")
    private String nome;

    @Email(message = "Email should be valid")
    private String email;

    private String senha;

}
