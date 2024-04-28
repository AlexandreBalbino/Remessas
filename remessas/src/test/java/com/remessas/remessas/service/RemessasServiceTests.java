package com.remessas.remessas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.remessas.remessas.dto.remessa.RemessaDto;
import com.remessas.remessas.entity.Carteira;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.enums.Origem;
import com.remessas.remessas.exception.RemessaException;
import com.remessas.remessas.exception.UsuarioInexistenteException;
import com.remessas.remessas.repository.RemessasRepository;
import com.remessas.remessas.repository.UsuariosRepository;

@SpringBootTest
public class RemessasServiceTests {
    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private RemessasRepository remessasRepository;

    @InjectMocks
    private RemessasService remessasService;

    final String remetente = "remetente@email.com";
    final String destinatario = "destinatario@email.com";

    @Test
    void Retorna_Erro_Caso_Remetente_Nao_Exista() throws Exception {
        var remessaDto = new RemessaDto();
        when(usuariosRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsuarioInexistenteException.class, () -> {
            remessasService.criarRemessa(remetente, remessaDto);
        });
    }

    @Test
    void Retorna_Erro_Caso_Destinatario_Nao_Exista() throws Exception {
        var remessaDto = new RemessaDto();
        remessaDto.setEmailDestinario(destinatario);

        var usuarioRemetente = Usuario.builder().build();

        when(usuariosRepository.findByEmail(remetente)).thenReturn(Optional.of(usuarioRemetente));
        when(usuariosRepository.findByEmail(destinatario)).thenReturn(Optional.empty());

        assertThrows(UsuarioInexistenteException.class, () -> {
            remessasService.criarRemessa(remetente, remessaDto);
        });
    }

    @Test
    void Retorna_Erro_Caso_Saldo_Insuficiente_Remetente() throws Exception {
        var remessaDto = new RemessaDto();
        remessaDto.setEmailDestinario(destinatario);
        remessaDto.setRemessa(new BigDecimal("20"));

        var usuarioRemetente = Usuario.builder()
                .carteiras(getCarteiras(BigDecimal.ZERO, BigDecimal.ZERO))
                .build();

        var usuarioDestinario = Usuario.builder()
                .carteiras(getCarteiras(BigDecimal.ZERO, BigDecimal.ZERO))
                .build();

        when(usuariosRepository.findByEmail(remetente)).thenReturn(Optional.of(usuarioRemetente));
        when(usuariosRepository.findByEmail(destinatario)).thenReturn(Optional.of(usuarioDestinario));

        var erro = assertThrows(RemessaException.class, () -> {
            remessasService.criarRemessa(remetente, remessaDto);
        });

        assertEquals("Saldo do remetente insuficiente", erro.getMessage());
    }

    @Test
    void Retorna_Sucesso() throws Exception {
        var valorRemessa = new BigDecimal("5.16");
        var resultadoSaldoRemetente = new BigDecimal("30");
        var resultadoSaldoDestinatario = new BigDecimal("1.00");
        var resultadoIgual = 0;

        var remessaDto = new RemessaDto();
        remessaDto.setEmailDestinario(destinatario);
        remessaDto.setRemessa(valorRemessa);

        var usuarioRemetente = Usuario.builder()
                .carteiras(getCarteiras(new BigDecimal("35.16"), BigDecimal.ZERO))
                .build();

        var usuarioDestinario = Usuario.builder()
                .carteiras(getCarteiras(BigDecimal.ZERO, BigDecimal.ZERO))
                .build();

        when(usuariosRepository.findByEmail(remetente)).thenReturn(Optional.of(usuarioRemetente));
        when(usuariosRepository.findByEmail(destinatario)).thenReturn(Optional.of(usuarioDestinario));

        var remessa = remessasService.criarRemessa(remetente, remessaDto);

        assertEquals(valorRemessa.compareTo(remessa.getRemessa()), resultadoIgual);
        assertEquals(resultadoSaldoRemetente.compareTo(remessa.getUsuarioRemetente().getCarteiraPt().getSaldo()),
                resultadoIgual);
        assertEquals(resultadoSaldoDestinatario.compareTo(remessa.getUsuarioDestinario().getCarteiraEn().getSaldo()),
                resultadoIgual);

    }

    private List<Carteira> getCarteiras(BigDecimal saldoPt, BigDecimal saldoEn) {
        var carteiraPt = Carteira.builder().saldo(saldoPt).origem(Origem.PT).build();
        var carteiraEn = Carteira.builder().saldo(saldoEn).origem(Origem.EN).build();

        List<Carteira> carteiras = new ArrayList<>();
        carteiras.add(carteiraPt);
        carteiras.add(carteiraEn);
        return carteiras;
    }

}