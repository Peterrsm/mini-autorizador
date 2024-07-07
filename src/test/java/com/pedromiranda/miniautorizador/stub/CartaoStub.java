package com.pedromiranda.miniautorizador.stub;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;

public class CartaoStub {
    public Cartao createCartao() {
        Cartao cartao = Cartao.CartaoBuilder.builder()
                .numeroCartao("1020304050")
                .senha("1234")
                .build();
        return cartao;
    }

    public CartaoDTO createDTO() {
        CartaoDTO dto = new CartaoDTO();
        dto.setSenha("1234");
        dto.setNumeroCartao("1020304050");
        return dto;
    }
}
