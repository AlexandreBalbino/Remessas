package com.remessas.remessas.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.remessas.remessas.exception.CotacoesException;
import com.remessas.remessas.http.CotacaoRequest;
import com.remessas.remessas.http.responses.CotacaoDiaResponse;
import com.remessas.remessas.http.responses.Parametro;
import com.remessas.remessas.util.DataUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotacoesService {
    private static final int MAXIMO_DIAS_ATRAS = 3;
    private static final int DIA_ANTERIOR = 1;
    private String ENDPOINT = "CotacaoDolarDia(dataCotacao=@dataCotacao)?";
    private final CotacaoRequest cotacaoRequest;

    public BigDecimal obtemCotacaoAtual() throws IOException, CotacoesException {
        LocalDate data = LocalDate.now();
        return getCotacaoDia(data);
    }

    private BigDecimal getCotacaoDia(LocalDate data) throws IOException, CotacoesException {

        var dataFormatada = "'" + DataUtil.formataData(data) + "'";
        List<Parametro> parametros = criaParametros(dataFormatada);

        var resposta = cotacaoRequest.get(ENDPOINT, parametros);
        var cotacaoDia = new Gson().fromJson(resposta, CotacaoDiaResponse.class);

        if (cotacaoDia.getValue().stream().findFirst().isPresent()) {
            return cotacaoDia.getValue().stream().findFirst().get().getCotacaoCompra();
        } else if (data.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ATRAS))) {
            throw new CotacoesException("Limite de busca pelo valor da cotação atingido");
        }

        data = data.minusDays(DIA_ANTERIOR);
        return getCotacaoDia(data);
    }

    private List<Parametro> criaParametros(String dataHojeFormatada) {
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(new Parametro("@dataCotacao", dataHojeFormatada));
        parametros.add(new Parametro("$top", "1"));
        parametros.add(new Parametro("$format", "json"));
        parametros.add(new Parametro("$select", "cotacaoCompra"));
        return parametros;
    }
}
