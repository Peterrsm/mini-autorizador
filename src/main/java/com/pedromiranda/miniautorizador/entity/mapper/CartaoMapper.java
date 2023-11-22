package com.pedromiranda.miniautorizador.entity.mapper;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import org.springframework.stereotype.Component;

@Component
public class CartaoMapper {
    public Cartao toCartao(CartaoDTO cartao_dto) {
        return Cartao.CartaoBuilder.builder()
                .numeroCartao(cartao_dto.getNumeroCartao())
                .senha(cartao_dto.getSenha())
                .build();
    }
}
