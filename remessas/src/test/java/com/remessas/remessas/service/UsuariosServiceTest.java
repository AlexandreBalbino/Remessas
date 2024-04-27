package com.remessas.remessas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.remessas.remessas.dto.usuario.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.dto.usuario.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Carteira;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.enums.Origem;
import com.remessas.remessas.exception.UsuarioExistenteException;
import com.remessas.remessas.mapper.CriarPessoaFisicaUsuarioDtoMapper;
import com.remessas.remessas.mapper.CriarPessoaJuridicaUsuarioDtoMapper;
import com.remessas.remessas.repository.UsuariosRepository;

@SpringBootTest
public class UsuariosServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private CriarPessoaFisicaUsuarioDtoMapper criarPessoaFisicaUsuarioDtoMapper;

    @Mock
    private CriarPessoaJuridicaUsuarioDtoMapper criarPessoaJuridicaUsuarioDtoMapper;

    @InjectMocks
    private UsuariosService usuariosService;

    @Test
    void Retorna_Null_Caso_Nao_Obtenha_Usuario() {

        when(usuariosRepository.findById(anyLong())).thenReturn(Optional.empty());

        var resposta = usuariosService.obtemUsuario(1L);

        assertNull(resposta);
    }

    @Test
    void Retorna_Sucesso_Caso_Obtenha_Usuario() {

        var nome = "Joao";
        var usuario = Usuario.builder().nome(nome).build();
        when(usuariosRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        var resposta = usuariosService.obtemUsuario(1L);

        assertEquals(resposta.getNome(), nome);
    }

    @Test
    void Retorna_Sucesso_Ao_Salvar_Usuario_Fisico() throws UsuarioExistenteException {
        var nome = "Joao";
        var cpfCnpj = "12345678912";
        var senha = "123";
        var senhaCriptografada = "202cb962ac59075b964b07152d234b70";
        var email = "testes@email.com";
        var id = 1L;

        var usuario = Usuario.builder().nome(nome).cpfCnpj(cpfCnpj).email(email).senha(senha).build();
        when(criarPessoaFisicaUsuarioDtoMapper.map(any())).thenReturn(usuario);

        var usuarioDto = new CriarPessoaFisicaUsuarioDto();
        usuarioDto.setCpfCnpj(cpfCnpj);
        usuarioDto.setNome(nome);
        usuarioDto.setSenha(senha);
        usuarioDto.setEmail(email);

        List<Usuario> usuarioExistente = new ArrayList<>();
        when(usuariosRepository.findByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(usuarioExistente);

        var usuarioPersistido = Usuario.builder().id(id).nome(nome).cpfCnpj(cpfCnpj).senha(senhaCriptografada)
                .email(email).build();
        when(usuariosRepository.saveAndFlush(any())).thenReturn(usuarioPersistido);

        usuarioPersistido.setCarteiras(getCarteiras(usuario));
        when(usuariosRepository.save(any())).thenReturn(usuarioPersistido);

        var resposta = usuariosService.salvaUsuarioPessoFisica(usuarioDto);

        assertEquals(resposta.getId(), id);
        assertEquals(resposta.getNome(), nome);
        assertEquals(resposta.getCpfCnpj(), cpfCnpj);
        assertEquals(resposta.getEmail(), email);
        assertEquals(resposta.getSenha(), senhaCriptografada);
        assertEquals(resposta.getCarteiras().size(), 2);

        var carteiraPt = resposta.getCarteiras().stream().filter(x -> x.getOrigem() == Origem.PT).findFirst().get();
        assertEquals(carteiraPt.getSaldo(), 0.00);

        var carteiraEn = resposta.getCarteiras().stream().filter(x -> x.getOrigem() == Origem.EN).findFirst().get();
        assertEquals(carteiraEn.getSaldo(), 0.00);

    }

    @Test
    void Retorna_Erro_Usuario_Existente_Ao_Salvar_Usuario_Fisico() throws UsuarioExistenteException {
        var nome = "Joao";
        var cpfCnpj = "12345678912";
        var senha = "123";
        var senhaCriptografada = "202cb962ac59075b964b07152d234b70";
        var email = "testes@email.com";
        var id = 1L;

        var usuario = Usuario.builder().nome(nome).cpfCnpj(cpfCnpj).senha(senha).email(email).build();
        when(criarPessoaFisicaUsuarioDtoMapper.map(any())).thenReturn(usuario);

        var usuarioDto = new CriarPessoaFisicaUsuarioDto();
        usuarioDto.setCpfCnpj(cpfCnpj);
        usuarioDto.setNome(nome);
        usuarioDto.setSenha(senha);

        List<Usuario> usuariosExistente = new ArrayList<>();
        var usuarioExistente = Usuario.builder().id(id).nome(nome).cpfCnpj(cpfCnpj).email(email)
                .senha(senhaCriptografada).build();
        usuariosExistente.add(usuarioExistente);
        when(usuariosRepository.findByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(usuariosExistente);

        assertThrows(UsuarioExistenteException.class, () -> {
            usuariosService.salvaUsuarioPessoFisica(usuarioDto);
        });

    }

    @Test
    void Retorna_Sucesso_Ao_Salvar_Usuario_Juridico() throws UsuarioExistenteException {
        var nome = "Joao";
        var cpfCnpj = "58027329035";
        var senha = "123";
        var senhaCriptografada = "202cb962ac59075b964b07152d234b70";
        var email = "testes@email.com";
        var id = 1L;

        var usuario = Usuario.builder().nome(nome).cpfCnpj(cpfCnpj).email(email).senha(senha).build();
        when(criarPessoaJuridicaUsuarioDtoMapper.map(any())).thenReturn(usuario);

        var usuarioDto = new CriarPessoaJuridicaUsuarioDto();
        usuarioDto.setCpfCnpj(cpfCnpj);
        usuarioDto.setNome(nome);
        usuarioDto.setSenha(senha);
        usuarioDto.setEmail(email);

        List<Usuario> usuarioExistente = new ArrayList<>();
        when(usuariosRepository.findByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(usuarioExistente);

        var usuarioPersistido = Usuario.builder().id(id).nome(nome).cpfCnpj(cpfCnpj).senha(senhaCriptografada)
                .email(email).build();
        when(usuariosRepository.saveAndFlush(any())).thenReturn(usuarioPersistido);

        usuarioPersistido.setCarteiras(getCarteiras(usuario));
        when(usuariosRepository.save(any())).thenReturn(usuarioPersistido);

        var resposta = usuariosService.salvaUsuarioPessoJuridica(usuarioDto);

        assertEquals(resposta.getId(), id);
        assertEquals(resposta.getNome(), nome);
        assertEquals(resposta.getCpfCnpj(), cpfCnpj);
        assertEquals(resposta.getEmail(), email);
        assertEquals(resposta.getSenha(), senhaCriptografada);
        assertEquals(resposta.getCarteiras().size(), 2);

        var carteiraPt = resposta.getCarteiras().stream().filter(x -> x.getOrigem() == Origem.PT).findFirst().get();
        assertEquals(carteiraPt.getSaldo(), 0.00);

        var carteiraEn = resposta.getCarteiras().stream().filter(x -> x.getOrigem() == Origem.EN).findFirst().get();
        assertEquals(carteiraEn.getSaldo(), 0.00);

    }

    @Test
    void Retorna_Erro_Usuario_Existente_Ao_Salvar_Usuario_Juridico() throws UsuarioExistenteException {
        var nome = "Joao";
        var cpfCnpj = "12345678912";
        var senha = "123";
        var senhaCriptografada = "202cb962ac59075b964b07152d234b70";
        var email = "testes@email.com";
        var id = 1L;

        var usuario = Usuario.builder().nome(nome).cpfCnpj(cpfCnpj).senha(senha).email(email).build();
        when(criarPessoaJuridicaUsuarioDtoMapper.map(any())).thenReturn(usuario);

        var usuarioDto = new CriarPessoaJuridicaUsuarioDto();
        usuarioDto.setCpfCnpj(cpfCnpj);
        usuarioDto.setNome(nome);
        usuarioDto.setSenha(senha);

        List<Usuario> usuariosExistente = new ArrayList<>();
        var usuarioExistente = Usuario.builder().id(id).nome(nome).cpfCnpj(cpfCnpj).email(email)
                .senha(senhaCriptografada).build();
        usuariosExistente.add(usuarioExistente);
        when(usuariosRepository.findByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(usuariosExistente);

        assertThrows(UsuarioExistenteException.class, () -> {
            usuariosService.salvaUsuarioPessoJuridica(usuarioDto);
        });

    }

    private List<Carteira> getCarteiras(Usuario usuario) {
        var carteiraPt = Carteira.builder().saldo(0.00).origem(Origem.PT).usuario(usuario).build();
        var carteiraEn = Carteira.builder().saldo(0.00).origem(Origem.EN).usuario(usuario).build();

        List<Carteira> carteiras = new ArrayList<>();
        carteiras.add(carteiraPt);
        carteiras.add(carteiraEn);
        return carteiras;
    }

}
