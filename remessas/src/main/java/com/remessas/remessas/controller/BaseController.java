package com.remessas.remessas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.remessas.remessas.exception.BaseException;

@ControllerAdvice
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleException(BaseException e) {

        if (e.getStatus() == HttpStatus.BAD_REQUEST) {
            logger.error("Erro de bad request", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String errorMessage = "Ocorreu um erro inesperado.";
        logger.error(errorMessage, e);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        String errorMessage = "Ocorreu um erro.";
        logger.error(errorMessage, e);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

}
