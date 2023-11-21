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

    public Cartao(Long id, String numeroCartao, String senha, String saldo) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = BigDecimal.valueOf(500);
    }

    public static Cartao builder() {
        return new Cartao();
    }
}
