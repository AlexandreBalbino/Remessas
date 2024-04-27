package com.remessas.remessas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.repository.UsuariosRepository;

@SpringBootTest
public class UsuariosServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;

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
}
