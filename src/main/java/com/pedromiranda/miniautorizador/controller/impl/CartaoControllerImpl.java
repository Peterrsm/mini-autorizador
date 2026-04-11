package com.pedromiranda.miniautorizador.controller.impl;

import com.pedromiranda.miniautorizador.controller.interfaces.ICartaoController;
import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.service.Impl.CartaoServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Cartões", description = "Endpoints para gestão e operações de cartões de débito")
public class CartaoControllerImpl implements ICartaoController {

    @Autowired
    CartaoServiceImpl service;

    @Override
    public ResponseEntity<ResponseCartaoSaldo> getCartaoByNumeroCartao(CardNumber numero_cartao) {
        ResponseCartaoSaldo retorno = service.getCartaoByNumeroCartao(numero_cartao);
        return ResponseEntity.ok()
                .body(retorno);
    }

    @Override
    public ResponseEntity<CartaoDTO> cadastraCartao(CartaoDTO cartao_dto) {
        CartaoDTO retorno = service.cadastraCartao(cartao_dto);
        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> realizaTransacao(Transacao transacao) {
        String retorno = service.realizaTransacao(transacao);
        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }
}
