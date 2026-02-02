package com.pedromiranda.miniautorizador.controller.impl;

import com.pedromiranda.miniautorizador.entity.CardNumber;
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
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
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

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static org.mockito.ArgumentMatchers.any;

@AnalyzeClasses(packages = "com.pedromiranda.miniautorizador")
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

        Mockito.when(service.getCartaoByNumeroCartao(new CardNumber("102030405060")))
                .thenReturn(ResponseCartaoSaldo.cartaoToResponseCartaoSaldo(cartao));

        ResponseEntity<ResponseCartaoSaldo> result = controller.getCartaoByNumeroCartao(new CardNumber("102030405060"));

        Assertions.assertNotNull(controller.getCartaoByNumeroCartao(new CardNumber("102030405060")));
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
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
        Assertions.assertEquals(result.getBody().getNumeroCartao(), "102030405060");
        Assertions.assertEquals(result.getBody().getSenha(), "12345678");

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

    @ArchTest
    public static final ArchRule service_accessed_only_by_controller = layeredArchitecture()
            .consideringAllDependencies()
            .layer("service").definedBy("..controller.impl");

    @ArchTest
    public static final ArchRule controller_must_reside_controller = classes()
            .that()
            .haveNameMatching(".*Controller")
            .should()
            .resideInAPackage("..controller.interfaces");

    @ArchTest
    public static final ArchRule controllerimpl_must_reside_controlle_impl = classes()
            .that()
            .haveNameMatching(".*ControllerImpl")
            .should()
            .resideInAPackage("..controller.impl");

}