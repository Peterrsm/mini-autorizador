package com.pedromiranda.miniautorizador.controller.impl;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
import com.pedromiranda.miniautorizador.service.CartaoService;
import com.pedromiranda.miniautorizador.service.exceptions.CardNotFoundException;
import com.pedromiranda.miniautorizador.service.exceptions.NoFundException;
import com.pedromiranda.miniautorizador.service.exceptions.WrongCardNumberException;
import com.pedromiranda.miniautorizador.service.exceptions.WrongPasswordException;
import com.pedromiranda.miniautorizador.stub.CartaoStub;
import com.pedromiranda.miniautorizador.stub.TransacaoStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CartaoControllerImplTest {

    @Mock
    CartaoService service;

    @Mock
    CartaoRepository repository;

    @InjectMocks
    CartaoControllerImpl controller;

    CartaoStub stub = new CartaoStub();
    TransacaoStub stub_transacao = new TransacaoStub();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCartaoByNumeroCartaoTest() {
        Cartao cartao = stub.createCartao();

        Mockito.when(service.getCartaoByNumeroCartao("1020304050"))
                .thenReturn(ResponseCartaoSaldo.cartaoToResponseCartaoSaldo(cartao));

        ResponseEntity<ResponseCartaoSaldo> result = controller.getCartaoByNumeroCartao("1020304050");

        Assertions.assertNotNull(controller.getCartaoByNumeroCartao("1020304050"));
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getBody().getSaldo(), BigDecimal.valueOf(500));
    }

    @Test
    void cadastraCartaoTest() {
        Cartao cartao = stub.createCartao();
        CartaoDTO cartao_dto = CartaoDTO.toDTO(cartao);

        Mockito.when(service.cadastraCartao(cartao_dto))
                .thenReturn(cartao_dto);

        ResponseEntity<CartaoDTO> result = controller.cadastraCartao(cartao_dto);

        Assertions.assertNotNull(controller.cadastraCartao(cartao_dto));
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(result.getBody().getNumeroCartao(), "1020304050");
        Assertions.assertEquals(result.getBody().getSenha(), "1234");

    }

    @Test
    void realizaTransacaoTest() {
        Transacao transacao = stub_transacao.createTransacao();

        ResponseEntity<String> result = controller.realizaTransacao(transacao);

        Assertions.assertNotNull(controller.realizaTransacao(transacao));
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void shouldThrowCardNotFoundException() {
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(service.realizaTransacao(any()))
                .thenThrow(CardNotFoundException.class);

        Assertions.assertThrows(CardNotFoundException.class, () -> controller.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowNoFundException() {
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(service.realizaTransacao(any()))
                .thenThrow(NoFundException.class);

        Assertions.assertThrows(NoFundException.class, () -> controller.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongPasswordException() {
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(service.realizaTransacao(any()))
                .thenThrow(WrongPasswordException.class);

        Assertions.assertThrows(WrongPasswordException.class, () -> controller.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongCardNumberException() {
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(service.realizaTransacao(any()))
                .thenThrow(WrongCardNumberException.class);

        Assertions.assertThrows(WrongCardNumberException.class, () -> controller.realizaTransacao(transacao));
    }
}