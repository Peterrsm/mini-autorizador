package com.pedromiranda.miniautorizador.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Senha {

    @Column(nullable = false)
    private String senha;

    @JsonValue
    public String getSenha() {
        return senha;
    }

    protected Senha() {
    }

    @JsonCreator
    public Senha(String senha) {
        validateSenha(senha);
        this.senha = senha;
    }

    private void validateSenha(String senha) {
        if (senha.length() < 8)
            throw new IllegalArgumentException("INVALID PASSWORD.");
    }

    @Override
    public String toString() {
        return this.senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senha senha1 = (Senha) o;
        return Objects.equals(senha, senha1.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senha);
    }
}
