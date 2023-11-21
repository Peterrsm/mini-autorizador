package com.pedromiranda.miniautorizador.controller.impl;

import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartaoControllerImpl {

    @Autowired
    CartaoService service;

    @GetMapping("/cartoes/{numero_cartao}")
    public ResponseEntity<ResponseCartaoSaldo> getCartaoByNumeroCartao(@PathVariable(value = "numero_cartao") String numero_cartao) {
        ResponseCartaoSaldo retorno = service.getCartaoByNumeroCartao(numero_cartao);
        return ResponseEntity.ok()
                .body(retorno);
    }

    @PostMapping("/cartoes")
    public ResponseEntity<CartaoDTO> cadastraCartao(@RequestBody CartaoDTO cartao_dto) {
        CartaoDTO retorno = service.cadastraCartao(cartao_dto);
        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    @PostMapping("/transacoes")
    public ResponseEntity<String> realizaTransacao(@RequestBody Transacao transacao) {
        String retorno = service.realizaTransacao(transacao);
        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }
}
