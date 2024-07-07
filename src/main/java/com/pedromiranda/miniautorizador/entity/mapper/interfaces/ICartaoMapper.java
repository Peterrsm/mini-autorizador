package com.pedromiranda.miniautorizador.entity.mapper.interfaces;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;

public interface ICartaoMapper {
    Cartao toCartao(CartaoDTO cartao_dto);
}
