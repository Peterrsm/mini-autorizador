package com.pedromiranda.miniautorizador.stub;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Senha;
import com.pedromiranda.miniautorizador.entity.Transacao;

import java.math.BigDecimal;

public class TransacaoStub {
    public Transacao createTransacao() {
        Transacao transacao = new Transacao();
        transacao.setSenhaCartao(new Senha("12345678"));
        transacao.setNumeroCartao(new CardNumber("102030405060"));
        transacao.setValor(BigDecimal.valueOf(100));
        return transacao;
    }
}
