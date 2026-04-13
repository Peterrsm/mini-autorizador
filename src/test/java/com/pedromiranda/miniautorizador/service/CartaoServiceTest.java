package com.pedromiranda.miniautorizador.service;

import com.pedromiranda.miniautorizador.entity.*;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.entity.mapper.CartaoMapper;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
import com.pedromiranda.miniautorizador.service.Impl.CartaoServiceImpl;
import com.pedromiranda.miniautorizador.service.exceptions.*;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    CartaoMapper mapper;

    @Mock
    CartaoRepository repository;

    @InjectMocks
    CartaoServiceImpl service;

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
        Assertions.assertEquals(result.getNumeroCartao(), "102030405060");
        Assertions.assertNotNull(result.getSenha(), "12345678");
    }

    @Test
    void getCartaoByNumeroCartaoTest() {
        Cartao cartao = stub.createCartao();

        Mockito.when(repository.findByNumeroCartaoCardNumber("102030405060"))
                .thenReturn(cartao);

        ResponseCartaoSaldo result = service.getCartaoByNumeroCartao(new CardNumber("102030405060"));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSaldo(), BigDecimal.valueOf(500));
    }

    @Test
    void shouldThrowExceptionWithNoDatabaseConnection() {
        Mockito.when(repository.findAll()).thenThrow(new RuntimeException("DB offline"));

        Assertions.assertThrows(RuntimeException.class, () -> service.getCartoes());
    }

    @Test
    void realizaTransacaoTest() {
        Cartao cartao = stub.createCartao();
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartaoCardNumber("102030405060"))
                .thenReturn(cartao);

        String result = service.realizaTransacao(transacao);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "Transação efetuada com sucesso.");
    }

    @Test
    void shouldReturnCardListWiothSuccess() {
        List<Cartao> listaMock = List.of(stub.createCartao());
        Mockito.when(repository.findAll()).thenReturn(listaMock);

        List<Cartao> resultado = service.getCartoes();

        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(1, resultado.size());

        Assertions.assertEquals(listaMock.get(0).getNumeroCartao(), resultado.get(0).getNumeroCartao());
    }

    @Test
    void shouldThrowCardNotFoundException() {
        CardNumber numeroNaoExistente = new CardNumber("000000000000");

        Mockito.when(repository.findByNumeroCartaoCardNumber(any())).thenReturn(null);

        Assertions.assertThrows(CardNotFoundException.class, () -> {
            service.getCartaoByNumeroCartao(numeroNaoExistente);
        });
    }

    @Test
    void shouldThrowNoFundException() {
        Cartao cartao = stub.createCartao();
        cartao.setSaldo(new Saldo(BigDecimal.valueOf(0)));
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartaoCardNumber("102030405060"))
                .thenReturn(cartao);

        Assertions.assertThrows(NoFundException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongPasswordException() {
        Cartao cartao = stub.createCartao();
        cartao.setSenha(new Senha("12345677"));
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartaoCardNumber("102030405060"))
                .thenReturn(cartao);

        Assertions.assertThrows(WrongPasswordException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowWrongCardNumberException() {
        Cartao cartao = stub.createCartao();
        cartao.setNumeroCartao(new CardNumber("102030405050"));
        Transacao transacao = stub_transacao.createTransacao();

        Mockito.when(repository.findByNumeroCartaoCardNumber("102030405060"))
                .thenReturn(cartao);

        Assertions.assertThrows(WrongCardNumberException.class, () -> service.realizaTransacao(transacao));
    }

    @Test
    void shouldThrowCardAlreadyInDatabaseException() {
        Cartao cartao = stub.createCartao();
        CartaoDTO dto = CartaoDTO.toDTO(cartao);

        Mockito.when(repository.save(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(CardAlreadyInDatabaseException.class, () -> {
            service.cadastraCartao(dto);
        });
    }

}