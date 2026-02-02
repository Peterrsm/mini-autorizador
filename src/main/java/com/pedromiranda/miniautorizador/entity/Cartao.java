package com.pedromiranda.miniautorizador.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "cardNumber", column = @Column(name = "numero_cartao", unique = true, nullable = false))
    private CardNumber numeroCartao;

    @Embedded
    private Senha senha;

    @Embedded
    private Saldo saldo;

    public Cartao() {

    }

    public Cartao(Long id, CardNumber numeroCartao, Senha senha) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = new Saldo(new BigDecimal(500));
    }

    public CardNumber getNumeroCartao() {
        return numeroCartao;
    }

    public Senha getSenha() {
        return senha;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public void setNumeroCartao(CardNumber numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public static class CartaoBuilder {
        private CardNumber numeroCartao;
        private Senha senha;

        public static CartaoBuilder builder() {
            return new CartaoBuilder();
        }

        public CartaoBuilder numeroCartao(CardNumber numeroCartao) {
            this.numeroCartao = numeroCartao;
            return this;
        }

        public CartaoBuilder senha(Senha senha) {
            this.senha = senha;
            return this;
        }

        public Cartao build() {
            return new Cartao(null, numeroCartao, senha);
        }
    }
}
