package com.pedromiranda.miniautorizador.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Saldo {

    @Column(nullable = false)
    private BigDecimal saldo;

    @JsonValue
    public BigDecimal getSaldo() {
        return saldo;
    }

    @JsonCreator
    public Saldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return this.saldo.toString();
    }
}
