package com.pedromiranda.miniautorizador.entity.exceptions;

import com.pedromiranda.miniautorizador.controller.exceptions.StandardError;
import com.pedromiranda.miniautorizador.service.exceptions.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.InvalidPropertiesFormatException;

@ControllerAdvice
public class CardNumberExceptionHandler {

    @ExceptionHandler(InvalidPropertiesFormatException.class)
    public ResponseEntity<StandardError> cardNotFoundException(CardNotFoundException ex, HttpServletRequest request) {
        String err = "CARD NUMBER NOT PERMITED";
        HttpStatus status = HttpStatus.BAD_REQUEST;
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
