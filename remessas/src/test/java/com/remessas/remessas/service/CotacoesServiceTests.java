package com.remessas.remessas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.remessas.remessas.exception.CotacoesException;
import com.remessas.remessas.http.CotacaoRequest;

@SpringBootTest
public class CotacoesServiceTests {

    @Mock
    private CotacaoRequest cotacaoRequest;

    @InjectMocks
    private CotacoesService cotacoesService;

    @Test
    void Retorna_Erro_Ao_Realizar_Request() throws IOException {

        when(cotacaoRequest.get(any(), any())).thenThrow(new IOException());

        assertThrows(IOException.class, () -> {
            cotacoesService.obtemCotacaoAtual();
        });
    }

    @Test
    void Retorna_Sucesso() throws IOException, CotacoesException {
        var repostaCotacao = "{\"value\":[{\"cotacaoCompra\": 5.11490}]}";
        var cotacaoMock = new BigDecimal("5.11490");
        when(cotacaoRequest.get(any(), any())).thenReturn(repostaCotacao);

        var cotacao = cotacoesService.obtemCotacaoAtual();

        assertEquals(cotacaoMock.compareTo(cotacao), 0);
    }

}
