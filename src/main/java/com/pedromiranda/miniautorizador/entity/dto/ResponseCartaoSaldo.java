package com.pedromiranda.miniautorizador.entity.dto;

import com.pedromiranda.miniautorizador.entity.Cartao;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class ResponseCartaoSaldo implements Serializable {
    private BigDecimal saldo;

    public static ResponseCartaoSaldo cartaoToResponseCartaoSaldo(Cartao cartao) {
        ResponseCartaoSaldo response = new ResponseCartaoSaldo();
        response.saldo = cartao.getSaldo().getSaldo();
        return response;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}