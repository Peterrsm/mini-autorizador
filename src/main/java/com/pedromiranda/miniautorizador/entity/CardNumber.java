package com.pedromiranda.miniautorizador.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CardNumber {

    @Column(name = "numero_cartao", nullable = false, unique = true)
    private String cardNumber;

    @JsonValue
    public String getCardNumber() {
        return cardNumber;
    }

    protected CardNumber() {
    }

    @JsonCreator
    public CardNumber(String cardNumber) {
        validateCardNumber(cardNumber);
        this.cardNumber = cardNumber;
    }

    private void validateCardNumber(String cardNumber) {
        if (cardNumber.length() != 12)
            throw new IllegalArgumentException("INVALID CARD NUMBER.");
    }

    @Override
    public String toString() {
        return this.cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }
}
