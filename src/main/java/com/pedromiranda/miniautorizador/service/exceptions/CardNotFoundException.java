package com.pedromiranda.miniautorizador.service.exceptions;

import lombok.Data;

@Data
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {
        super("Não encontrado");
    }
}
