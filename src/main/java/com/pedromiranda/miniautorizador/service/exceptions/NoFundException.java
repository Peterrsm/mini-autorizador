package com.pedromiranda.miniautorizador.service.exceptions;

public class NoFundException extends RuntimeException {

    public NoFundException() {
        super("Erro com saldo");
    }
}
