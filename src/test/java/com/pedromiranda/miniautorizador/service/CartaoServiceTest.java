package com.pedromiranda.miniautorizador.service;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.entity.mapper.CartaoMapper;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    CartaoMapper mapper;

    @Mock
    CartaoRepository repository;

    @InjectMocks
    CartaoService service;

    CartaoStub stub = new CartaoStub();
    TransacaoStub stub_transacao = new TransacaoStub();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastraCartaoTest() {
        Cartao cartao = stub.createCartao();
        CartaoDTO dto = stub.createDTO();

        Mockito.when(repository.save(cartao))
                .thenReturn(cartao);

        Mockito.when(mapper.toCartao(dto))
                .thenReturn(cartao);

        CartaoDTO result = service.cadastraCartao(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getNumeroCartao(), "1020304050");
        Assertions.assertNotNull(result.getSenha(), "1234");
    }

    @Test
    void getCartaoByNumeroCartaoTest() {
        Cartao cartao = stub.createCartao();

        Mockito.when(repository.findByNumeroCartao("1020304050"))
                .thenReturn(cartao);

        ResponseCartaoSaldo result = service.getCartaoByNumeroCartao("1020304050");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSaldo(), BigDecimal.valueOf(500));
    }

    @Test
    void realizaTransacaoTest() {
        Cartao cartao = stub.createCartao();
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartao("1020304050"))
                .thenReturn(cartao);

        String result = service.realizaTransacao(transacao);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "Transação efetuada com sucesso.");
    }

    @Test
    void shouldThrowCardNotFoundException() {
        Cartao cartao = stub.createCartao();
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartao(anyString()))
                .thenReturn(null);


        Assertions.assertThrows(CardNotFoundException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowNoFundException() {
        Cartao cartao = stub.createCartao();
        cartao.setSaldo(BigDecimal.valueOf(0));
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartao("1020304050"))
                .thenReturn(cartao);

        Assertions.assertThrows(NoFundException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongPasswordException() {
        Cartao cartao = stub.createCartao();
        cartao.setSenha("12");
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartao("1020304050"))
                .thenReturn(cartao);

        Assertions.assertThrows(WrongPasswordException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongCardNumberException() {
        Cartao cartao = stub.createCartao();
        cartao.setNumeroCartao("54321");
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartao("1020304050"))
                .thenReturn(cartao);

        Assertions.assertThrows(WrongCardNumberException.class, () -> service.realizaTransacao(transacao));
    }
}