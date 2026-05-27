package com.pedromiranda.miniautorizador.entity.dto;

import com.pedromiranda.miniautorizador.entity.Cartao;

import java.math.BigDecimal;

public record ResponseCartaoSaldo(BigDecimal saldo) {

    public static ResponseCartaoSaldo from(Cartao cartao) {
        return new ResponseCartaoSaldo(cartao.getSaldo().getSaldo());
    }
}