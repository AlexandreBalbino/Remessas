package com.remessas.remessas.mapper;

import org.springframework.stereotype.Component;

import com.remessas.remessas.dto.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Usuario;

@Component
public class CriarPessoaJuridicaUsuarioDtoMapper implements Mapper<CriarPessoaJuridicaUsuarioDto, Usuario> {

    @Override
    public Usuario map(CriarPessoaJuridicaUsuarioDto objeto) {

        return Usuario
                .builder()
                .email(objeto.getEmail())
                .cpfCnpj(objeto.getCpfCnpj())
                .senha(objeto.getSenha())
                .nome(objeto.getNome())
                .build();
    }

}
