package com.pedromiranda.miniautorizador.service.exceptions;

public class WrongCardNumberException extends RuntimeException {

    public WrongCardNumberException() {
        super("Numero inválido.");
    }
}
