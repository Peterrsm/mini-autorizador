package com.pedromiranda.miniautorizador.controller.interfaces;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ICartaoController {

    @Operation(summary = "Pesquisa cartões", description = "Retorna todos os cartões cadastrados no banco de dados")
    @GetMapping("/cartoes")
    ResponseEntity<List<CartaoDTO>> getCartoes();

    @Operation(summary = "Pesquisa cartão por Número", description = "Retorna existência do cartão com base no número inserido")
    @GetMapping("/cartoes/{numero_cartao}")
    ResponseEntity<ResponseCartaoSaldo> getCartaoByNumeroCartao(@PathVariable(value = "numero_cartao") CardNumber numero_cartao);

    @Operation(summary = "Cadastra cartão", description = "Insere novo cartão no banco de dados")
    @PostMapping("/cartoes")
    ResponseEntity<CartaoDTO> cadastraCartao(@Valid @RequestBody CartaoDTO cartao_dto);

    @Operation(summary = "Realiza uma transação", description = "Valida senha, saldo e existência do cartão antes de debitar")
    @PostMapping("/transacoes")
    ResponseEntity<String> realizaTransacao(@Valid @RequestBody Transacao transacao);

}