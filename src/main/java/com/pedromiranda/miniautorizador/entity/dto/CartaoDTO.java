package com.pedromiranda.miniautorizador.entity.dto;

import com.pedromiranda.miniautorizador.entity.Cartao;

public record CartaoDTO(String numeroCartao, String senha, String saldo) {

    public static CartaoDTO toDTO(Cartao cartao) {
        return new CartaoDTO(
                cartao.getNumeroCartao().toString(),
                cartao.getSenha().toString(),
                cartao.getSaldo().toString()
        );
    }
}