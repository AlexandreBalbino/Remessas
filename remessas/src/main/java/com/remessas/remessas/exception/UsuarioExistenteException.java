package com.remessas.remessas.exception;

import org.springframework.http.HttpStatus;

public class UsuarioExistenteException extends BaseException {
    public UsuarioExistenteException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
