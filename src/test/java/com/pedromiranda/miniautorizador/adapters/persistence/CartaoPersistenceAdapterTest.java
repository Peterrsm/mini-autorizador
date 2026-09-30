package com.pedromiranda.miniautorizador.adapters.persistence;

import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
import com.pedromiranda.miniautorizador.stub.CartaoStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartaoPersistenceAdapterTest {

    @Mock
    CartaoRepository repository;

    @InjectMocks
    CartaoPersistenceAdapter adapter;

    @Test
    void findByNumeroCartaoCardNumber() {
        CartaoStub stub = new CartaoStub();
        Cartao expected = stub.createCartao();
        String numero = expected.getNumeroCartao().getCardNumber();

        Mockito.when(repository.findByNumeroCartaoCardNumber(numero))
                .thenReturn(expected);

        Cartao result = adapter.findByNumeroCartaoCardNumber(numero);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getNumeroCartao(), result.getNumeroCartao());
        Mockito.verify(repository).findByNumeroCartaoCardNumber(numero);
    }
}