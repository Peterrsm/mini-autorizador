package com.pedromiranda.miniautorizador.service.interfaces;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;

import java.util.List;

public interface ICartaoService {

    CartaoDTO cadastraCartao(CartaoDTO cartao_dto);

    ResponseCartaoSaldo getCartaoByNumeroCartao(CardNumber numero_cartao);

    List<Cartao> getCartoes();

    String realizaTransacao(Transacao transacao);

}