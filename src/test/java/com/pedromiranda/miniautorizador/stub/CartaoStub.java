package com.pedromiranda.miniautorizador.stub;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;

import java.math.BigDecimal;

public class CartaoStub {
    public Cartao createCartao() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1020304050");
        cartao.setSaldo(BigDecimal.valueOf(500));
        cartao.setSenha("1234");
        return cartao;
    }

    public CartaoDTO createDTO() {
        CartaoDTO dto = new CartaoDTO();
        dto.setSenha("1234");
        dto.setNumeroCartao("1020304050");
        return dto;
    }
}
