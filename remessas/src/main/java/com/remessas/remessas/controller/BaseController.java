package com.remessas.remessas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.remessas.remessas.exception.BaseException;

@ControllerAdvice
public class BaseController {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleException(BaseException e) {

        if (e.getStatus() == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String errorMessage = "Ocorreu um erro.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        String errorMessage = "Ocorreu um erro.";
        return ResponseEntity.internalServerError().body(errorMessage);
    }

}
