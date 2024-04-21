package com.pedromiranda.miniautorizador.controller.exceptions;

import com.pedromiranda.miniautorizador.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<StandardError> cardNotFoundException(CardNotFoundException ex, HttpServletRequest request) {
        String err = "CARD DO NOT EXIST";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .message(err)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(CardAlreadyInDatabaseException.class)
    public ResponseEntity<StandardError> cardAlreadyInDatabase(CardAlreadyInDatabaseException ex, HttpServletRequest request) {
        String err = "JÁ EXISTE UM CARTÃO COM ESTA NUMERAÇÃO";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .message(err)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(WrongCardNumberException.class)
    public ResponseEntity<StandardError> wrongCardNumber(WrongCardNumberException ex, HttpServletRequest request) {
        String err = "CARTAO INEXISTENTE ";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .message(err)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<StandardError> wrongPassword(WrongPasswordException ex, HttpServletRequest request) {
        String err = "SENHA INVALIDA";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .message(err)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(NoFundException.class)
    public ResponseEntity<StandardError> wrongPassword(NoFundException ex, HttpServletRequest request) {
        String err = "SALDO INSUFICIENTE";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .message(err)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }

}
