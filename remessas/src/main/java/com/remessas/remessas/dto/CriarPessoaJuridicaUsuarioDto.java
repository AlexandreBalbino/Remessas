package com.remessas.remessas.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarPessoaJuridicaUsuarioDto extends CriarUsuarioDto {
    @NotNull(message = "Cnpj é obrigatório")
    @CNPJ(message = "Cnpj inválido")
    private String cpfCnpj;
}
