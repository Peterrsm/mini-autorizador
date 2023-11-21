package com.pedromiranda.miniautorizador.repository;

import com.pedromiranda.miniautorizador.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    public Cartao findByNumeroCartao(String numero);
}
