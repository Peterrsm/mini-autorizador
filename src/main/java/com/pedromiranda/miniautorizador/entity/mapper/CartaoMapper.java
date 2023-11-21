package com.pedromiranda.miniautorizador.entity.mapper;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartaoMapper {
    public Cartao toCartao(CartaoDTO cartao_dto) {
        Cartao cartao = Cartao.builder();
        cartao.setNumeroCartao(cartao_dto.getNumeroCartao());
        cartao.setSenha(cartao_dto.getSenha());
        cartao.setSaldo(BigDecimal.valueOf(500));

        return cartao;
    }
}
