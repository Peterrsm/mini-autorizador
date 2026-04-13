package com.pedromiranda.miniautorizador.service.Impl;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Cartao;
import com.pedromiranda.miniautorizador.entity.Saldo;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.entity.dto.ResponseCartaoSaldo;
import com.pedromiranda.miniautorizador.entity.mapper.CartaoMapper;
import com.pedromiranda.miniautorizador.repository.CartaoRepository;
import com.pedromiranda.miniautorizador.service.exceptions.*;
import com.pedromiranda.miniautorizador.service.interfaces.ICartaoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartaoServiceImpl implements ICartaoService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CartaoServiceImpl.class);
    @Autowired
    CartaoMapper mapper;

    @Autowired
    CartaoRepository repository;

    @Override
    public CartaoDTO cadastraCartao(CartaoDTO cartao_dto) {
        try {
            log.info("Inserindo novo cartão com número: {}", cartao_dto.getNumeroCartao());
            log.info("{}", cartao_dto.toString());
            Cartao cartao = mapper.toCartao(cartao_dto);

            return CartaoDTO.toDTO(repository.save(cartao));

        } catch (Exception ex) {
            throw new CardAlreadyInDatabaseException();
        }
    }

    @Override
    public ResponseCartaoSaldo getCartaoByNumeroCartao(CardNumber numero_cartao) {
        Cartao cartao = repository.findByNumeroCartaoCardNumber(numero_cartao.getCardNumber());

        if (cartao == null) {
            throw new CardNotFoundException();
        }

        return ResponseCartaoSaldo.cartaoToResponseCartaoSaldo(cartao);
    }

    @Override
    public List getCartoes() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar todos os cartões", e);
            throw e;
        }
    }

    @Override
    public String realizaTransacao(Transacao transacao) {
        try {
            Cartao cartao = repository.findByNumeroCartaoCardNumber(transacao.getNumeroCartao());

            validateSaldo(cartao, transacao);

            log.info("Efetuando transferencia de R${} do cartão de numeração {}",
                    transacao.getValor(),
                    transacao.getNumeroCartao());
            try {
                cartao.setSaldo(new Saldo(cartao.getSaldo().getSaldo().subtract(transacao.getValor())));

                repository.save(cartao);

                log.info("Transação efetuada com sucesso.");
            } catch (Exception e) {
                log.error(e.getMessage());
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
