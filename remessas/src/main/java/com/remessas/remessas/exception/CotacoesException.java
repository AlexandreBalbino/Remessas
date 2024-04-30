package com.remessas.remessas.exception;

import org.springframework.http.HttpStatus;

public class CotacoesException extends BaseException {

    public CotacoesException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
