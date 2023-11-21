package com.pedromiranda.miniautorizador.entity.dto;

import com.pedromiranda.miniautorizador.entity.Cartao;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaoDTO {
    private String numeroCartao;
    private String senha;

    public static CartaoDTO toDTO(Cartao cartao) {
        CartaoDTO dto = new CartaoDTO();
        dto.setNumeroCartao(cartao.getNumeroCartao());
        dto.setSenha(cartao.getSenha());

        return dto;
    }

    @Override
    public String toString() {
        return "CartaoDTO{" +
                "numeroCartao='" + numeroCartao + '\'' +
                ", senna='" + senha + '\'' +
                '}';
    }
}