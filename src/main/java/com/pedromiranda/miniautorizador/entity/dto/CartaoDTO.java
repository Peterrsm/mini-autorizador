package com.pedromiranda.miniautorizador.entity.dto;

import com.pedromiranda.miniautorizador.entity.Cartao;

public class CartaoDTO {
    private String numeroCartao;
    private String senha;

    public static CartaoDTO toDTO(Cartao cartao) {
        CartaoDTO dto = new CartaoDTO();
        dto.setNumeroCartao(cartao.getNumeroCartao().toString());
        dto.setSenha(cartao.getSenha().toString());

        return dto;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "CartaoDTO{" +
                "numeroCartao='" + numeroCartao + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}