package com.remessas.remessas.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriarUsuarioDto {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    private String senha;

}
