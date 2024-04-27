package com.remessas.remessas.mapper;

import org.springframework.stereotype.Component;

import com.remessas.remessas.dto.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.entity.Usuario;

@Component
public class CriarPessoaFisicaUsuarioDtoMapper implements Mapper<CriarPessoaFisicaUsuarioDto, Usuario> {

    @Override
    public Usuario map(CriarPessoaFisicaUsuarioDto objeto) {
        return Usuario
                .builder()
                .email(objeto.getEmail())
                .cpfCnpj(objeto.getCpfCnpj())
                .senha(objeto.getSenha())
                .nome(objeto.getNome())
                .build();
    }

}
