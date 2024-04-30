package com.remessas.remessas.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.remessas.remessas.http.responses.Parametro;

@Component
public class CotacaoRequest {

    @Value("${remessas.url}")
    private String baseUrl;

    public String get(String endpoint, List<Parametro> parametros) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {

            StringBuilder url = new StringBuilder(baseUrl);
            url.append(endpoint);

            parametros.stream().forEach(parametro -> {
                var parametroConcatenado = "&" + parametro.nome + "=" + parametro.valor;
                url.append(parametroConcatenado);
            });

            HttpGet httpGet = new HttpGet(url.toString());
            HttpResponse response = httpClient.execute(httpGet);

            String responseBody = EntityUtils.toString(response.getEntity());
            httpGet.releaseConnection();
            return responseBody;
        }
    }
}
