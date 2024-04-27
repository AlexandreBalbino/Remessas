package com.remessas.remessas.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarPessoaJuridicaUsuarioDto extends CriarUsuarioDto {
    @NotNull
    @CNPJ
    private String cpfCnpj;
}
