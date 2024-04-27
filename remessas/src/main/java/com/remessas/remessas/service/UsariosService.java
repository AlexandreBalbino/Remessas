package com.remessas.remessas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.remessas.remessas.dto.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.dto.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.exception.UsuarioExistenteException;
import com.remessas.remessas.mapper.CriarPessoaFisicaUsuarioDtoMapper;
import com.remessas.remessas.mapper.CriarPessoaJuridicaUsuarioDtoMapper;
import com.remessas.remessas.repository.UsariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsariosService {

    final UsariosRepository usuarioRepository;
    final CriarPessoaFisicaUsuarioDtoMapper criarPessoaFisicaUsuarioDtoMapper;
    final CriarPessoaJuridicaUsuarioDtoMapper criarPessoaJuridicaUsuarioDtoMapper;

    public Usuario obtemUsuario(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario saveUsuarioPessoFisica(CriarPessoaFisicaUsuarioDto usuarioDto) throws UsuarioExistenteException {
        var usuario = criarPessoaFisicaUsuarioDtoMapper.map(usuarioDto);
        validaUsuarioExistente(usuario);
        return usuarioRepository.saveAndFlush(usuario);

    }

    public Usuario saveUsuarioPessoJuridica(CriarPessoaJuridicaUsuarioDto usuarioDto) throws UsuarioExistenteException {
        var usuario = criarPessoaJuridicaUsuarioDtoMapper.map(usuarioDto);
        validaUsuarioExistente(usuario);
        return usuarioRepository.saveAndFlush(usuario);

    }

    private void validaUsuarioExistente(Usuario usuario) throws UsuarioExistenteException {
        var usuariosBanco = usuarioRepository.findByEmailOrCpfCnpj(usuario.getEmail(), usuario.getCpfCnpj());
        if (!usuariosBanco.isEmpty()) {
            identificaCampoExistente(usuario, usuariosBanco);
        }
    }

    private void identificaCampoExistente(Usuario usuario, List<Usuario> usuariosBanco)
            throws UsuarioExistenteException {
        boolean emailExiste = usuariosBanco.stream().filter(x -> x.getEmail().equals(usuario.getEmail()))
                .findFirst().isPresent();
        if (emailExiste) {
            throw new UsuarioExistenteException("Email já existente " + usuario.getEmail());
        }

        boolean cpfCnpjExiste = usuariosBanco.stream().filter(x -> x.getCpfCnpj().equals(usuario.getCpfCnpj()))
                .findFirst().isPresent();
        if (cpfCnpjExiste) {
            throw new UsuarioExistenteException("Cpf/Cnpj já existente " + usuario.getEmail());
        }
    }

}
