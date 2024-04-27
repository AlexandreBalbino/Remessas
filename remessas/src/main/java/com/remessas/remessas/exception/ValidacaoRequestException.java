package com.remessas.remessas.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

public class ValidacaoRequestException extends BaseException {

    public ValidacaoRequestException(List<ObjectError> errors) {
        super(getMessage(errors), HttpStatus.BAD_REQUEST);
    }

    private static String getMessage(List<ObjectError> errors) {
        List<String> messages = errors.stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        return String.join("; ", messages);
    }
}
