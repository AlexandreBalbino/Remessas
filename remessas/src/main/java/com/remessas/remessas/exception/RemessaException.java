package com.remessas.remessas.exception;

import org.springframework.http.HttpStatus;

public class RemessaException extends BaseException {

    public RemessaException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
