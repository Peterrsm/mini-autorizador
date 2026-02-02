package com.pedromiranda.miniautorizador.service;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Saldo;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.entity.mapper.CartaoMapper;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
import com.pedromiranda.miniautorizador.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    CartaoMapper mapper;

    @Autowired
    CartaoRepository repository;

    public CartaoDTO cadastraCartao(CartaoDTO cartao_dto) {
        try {
            System.out.println("Inserindo novo cartão...");
            System.out.println(cartao_dto.toString());
            Cartao cartao = mapper.toCartao(cartao_dto);

            return CartaoDTO.toDTO(repository.save(cartao));

        } catch (Exception ex) {
            throw new CardAlreadyInDatabaseException();
        }
    }

    public ResponseCartaoSaldo getCartaoByNumeroCartao(CardNumber numero_cartao) {
        Cartao cartao = repository.findByNumeroCartaoCardNumber(numero_cartao.getCardNumber());

        if (cartao == null) {
            throw new CardNotFoundException();
        }

        return ResponseCartaoSaldo.cartaoToResponseCartaoSaldo(cartao);
    }

    public String realizaTransacao(Transacao transacao) {
        try {
            Cartao cartao = repository.findByNumeroCartaoCardNumber(transacao.getNumeroCartao());

            validateSaldo(cartao, transacao);

            System.out.println("Efetuando transferencia de R$" + transacao.getValor() + " do cartão de numeração: " +
                    transacao.getNumeroCartao());

            try {
                cartao.setSaldo(new Saldo(cartao.getSaldo().getSaldo().subtract(transacao.getValor())));

                System.out.println("Transação efetuada com sucesso.");

                repository.save(cartao);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NoFundException e) {
            throw new NoFundException();
        } catch (WrongPasswordException e) {
            throw new WrongPasswordException();
        } catch (WrongCardNumberException e) {
            throw new WrongCardNumberException();
        } catch (CardNotFoundException e) {
            throw new CardNotFoundException();
        }

        return "Transação efetuada com sucesso.";
    }

    private void validateSaldo(Cartao cartao, Transacao transacao) {
        if (cartao == null) {
            throw new CardNotFoundException();
        }

        if (!cartao.getNumeroCartao().getCardNumber().equals(transacao.getNumeroCartao())) {
            throw new WrongCardNumberException();
        }

        if (!cartao.getSenha().getSenha().equals(transacao.getSenhaCartao())) {
            throw new WrongPasswordException();
        }

        if (cartao.getSaldo().getSaldo().compareTo(transacao.getValor()) < 0) {
            throw new NoFundException();
        }
    }
}
