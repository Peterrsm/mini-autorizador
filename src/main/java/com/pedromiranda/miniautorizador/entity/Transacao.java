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

    public static Transacao builder() {
        return new Transacao();
    }
}
