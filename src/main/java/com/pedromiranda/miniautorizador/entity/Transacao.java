package com.pedromiranda.miniautorizador.entity;

import javax.persistence.Column;
import java.math.BigDecimal;

public class Transacao {

    @Column(nullable = false)
    private CardNumber numeroCartao;

    @Column(nullable = false)
    private Senha senhaCartao;

    @Column(nullable = false)
    private BigDecimal valor;

    public Transacao() {

    }

    public Transacao(CardNumber numeroCartao, Senha senhaCartao, BigDecimal valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public void setNumeroCartao(CardNumber numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public void setSenhaCartao(Senha senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getSenhaCartao() {
        return senhaCartao.getSenha();
    }

    public String getNumeroCartao() {
        return numeroCartao.getCardNumber();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public static class TransacaoBuilder {
        private String numeroCartao;
        private String senhaCartao;
        private BigDecimal valor;


        public static TransacaoBuilder builder() {
            return new TransacaoBuilder();
        }

        public TransacaoBuilder numeroCartao(String numeroCartao) {
            this.numeroCartao = numeroCartao;
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
            return new Transacao(new CardNumber(numeroCartao), new Senha(senhaCartao), valor);
        }
    }
}
