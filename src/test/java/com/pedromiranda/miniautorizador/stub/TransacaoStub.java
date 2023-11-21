package com.pedromiranda.miniautorizador.stub;

import com.pedromiranda.miniautorizador.entity.Transacao;

import java.math.BigDecimal;

public class TransacaoStub {
    public Transacao createTransacao() {
        Transacao transacao = new Transacao();
        transacao.setSenhaCartao("1234");
        transacao.setNumeroCartao("1020304050");
        transacao.setValor(BigDecimal.valueOf(100));
        return transacao;
    }
}
