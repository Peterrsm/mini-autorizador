package com.pedromiranda.miniautorizador.controller.interfaces;

import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ICartaoController {

    @GetMapping("/cartoes/{numero_cartao}")
    ResponseEntity<ResponseCartaoSaldo> getCartaoByNumeroCartao(@PathVariable(value = "numero_cartao") String numero_cartao);

    @PostMapping("/cartoes")
    ResponseEntity<CartaoDTO> cadastraCartao(@Valid @RequestBody CartaoDTO cartao_dto);

    @PostMapping("/transacoes")
    ResponseEntity<String> realizaTransacao(@Valid @RequestBody Transacao transacao);
}