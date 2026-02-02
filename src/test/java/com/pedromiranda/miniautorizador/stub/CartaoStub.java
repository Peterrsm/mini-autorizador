package com.pedromiranda.miniautorizador.stub;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Senha;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;

public class CartaoStub {
    public Cartao createCartao() {
        return Cartao.CartaoBuilder.builder()
                .numeroCartao(new CardNumber("102030405060"))
                .senha(new Senha("12345678"))
                .build();
    }

    public CartaoDTO createDTO() {
        Cartao cartao = new Cartao();

        cartao.setSenha(new Senha("12345678"));
        cartao.setNumeroCartao(new CardNumber("102030405060"));

        return CartaoDTO.toDTO(cartao);
    }
}
