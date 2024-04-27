package com.remessas.remessas.exception;

import org.springframework.http.HttpStatus;

public class UsuarioInexistenteException extends BaseException {

    public UsuarioInexistenteException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
