package com.remessas.remessas.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarPessoaFisicaUsuarioDto extends CriarUsuarioDto {

    @NotNull
    @CPF
    private String cpfCnpj;
}
