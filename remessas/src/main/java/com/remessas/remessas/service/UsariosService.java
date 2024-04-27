package com.remessas.remessas.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.remessas.remessas.dto.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.dto.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Carteira;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.enums.Origem;
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

    public Usuario salvaUsuarioPessoFisica(CriarPessoaFisicaUsuarioDto usuarioDto)
            throws UsuarioExistenteException, NoSuchAlgorithmException {
        var usuario = criarPessoaFisicaUsuarioDtoMapper.map(usuarioDto);
        return salvaUsuario(usuario);
    }

    public Usuario salvaUsuarioPessoJuridica(CriarPessoaJuridicaUsuarioDto usuarioDto)
            throws UsuarioExistenteException, NoSuchAlgorithmException {
        var usuario = criarPessoaJuridicaUsuarioDtoMapper.map(usuarioDto);
        return salvaUsuario(usuario);
    }

    private Usuario salvaUsuario(Usuario usuario) throws UsuarioExistenteException, NoSuchAlgorithmException {
        validaUsuarioExistente(usuario);
        usuario.criptografaSenha();
        var usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        usuario.setCarteiras(criaCarteiras(usuario));
        return usuarioRepository.save(usuarioSalvo);
    }

    private List<Carteira> criaCarteiras(Usuario usuario) {
        var carteiraPt = Carteira.builder().saldo(0.00).origem(Origem.PT).usuario(usuario).build();
        var carteiraEn = Carteira.builder().saldo(0.00).origem(Origem.EN).usuario(usuario).build();

        List<Carteira> carteiras = new ArrayList<>();
        carteiras.add(carteiraPt);
        carteiras.add(carteiraEn);
        return carteiras;
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
