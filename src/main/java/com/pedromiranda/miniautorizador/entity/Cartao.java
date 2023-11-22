package com.pedromiranda.miniautorizador.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private BigDecimal saldo;

    public Cartao() {

    }

    public Cartao(Long id, String numeroCartao, String senha) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = BigDecimal.valueOf(500);
    }

    public static class CartaoBuilder {
        private String numeroCartao;
        private String senha;

        public static CartaoBuilder builder() {
            return new CartaoBuilder();
        }

        public CartaoBuilder numeroCartao(String numeroCartao) {
            this.numeroCartao = numeroCartao;
            return this;
        }

        public CartaoBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Cartao build() {
            return new Cartao(null, numeroCartao, senha);
        }
    }
}
