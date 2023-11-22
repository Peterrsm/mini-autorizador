package com.pedromiranda.miniautorizador.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
public class Transacao {

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String senhaCartao;

    @Column(nullable = false)
    private BigDecimal valor;

    public Transacao() {

    }

    public Transacao(String numeroCartao, String senhaCartao, BigDecimal valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public static class TransacaoBuilder {
        private String numeroCartao;
        private String senhaCartao;
        private BigDecimal valor;


        public static TransacaoBuilder builder() {
            return new TransacaoBuilder();
        }

        public TransacaoBuilder numeroCartao(String numeroCartao) {
            this.senhaCartao = numeroCartao;
            return this;
        }

        public TransacaoBuilder senhaCartao(String senhaCartao) {
            this.senhaCartao = senhaCartao;
            return this;
        }

        public TransacaoBuilder valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Transacao build() {
            return new Transacao(numeroCartao, senhaCartao, valor);
        }
    }
}
