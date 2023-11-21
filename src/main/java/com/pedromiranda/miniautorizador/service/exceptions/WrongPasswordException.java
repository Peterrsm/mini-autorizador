package com.pedromiranda.miniautorizador.service.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Senha incorreta.");
    }
}
