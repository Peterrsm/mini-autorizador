package com.pedromiranda.miniautorizador.service.exceptions;

import lombok.Data;

@Data
public class CardAlreadyInDatabaseException extends RuntimeException {

    public CardAlreadyInDatabaseException() {
        super("Erro na numeração");
    }
}
