package com.remessas.remessas.http.responses;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CotacaoDiaResponse {
    public ArrayList<ValueResponse> value;
}
