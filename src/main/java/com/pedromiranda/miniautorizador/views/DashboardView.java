package com.pedromiranda.miniautorizador.views;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Senha;
import com.pedromiranda.miniautorizador.entity.Transacao;
import com.pedromiranda.miniautorizador.entity.dto.CartaoDTO;
import com.pedromiranda.miniautorizador.service.Impl.CartaoServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Route("dashboard")
@AnonymousAllowed
public class DashboardView extends VerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(DashboardView.class);
    private final CartaoServiceImpl service;

    // 1. ALTERADO: Tipagem alterada de Grid<Cartao> para Grid<CartaoDTO>
    private final Grid<CartaoDTO> grid = new Grid<>(CartaoDTO.class, false);

    public DashboardView(CartaoServiceImpl service) {
        this.service = service;

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        add(new H1("Mini Autorizador - Painel de Controle"));
        add(criarToolbar());

        configurarGrid();
        add(grid);

        atualizarGrid();
    }

    private HorizontalLayout criarToolbar() {
        Button btnNovoCartao = new Button("Novo Cartão", e -> abrirModalCadastro());
        btnNovoCartao.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnNovaTransacao = new Button("Realizar Transação", e -> abrirModalTransacao());
        btnNovaTransacao.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        return new HorizontalLayout(btnNovoCartao, btnNovaTransacao);
    }

    private void configurarGrid() {
        grid.addColumn(CartaoDTO::numeroCartao).setHeader("Número do Cartão").setAutoWidth(true);

        grid.addColumn(CartaoDTO::senha).setHeader("Senha (Hash/Texto)").setAutoWidth(true);

        grid.setHeight("400px");
        grid.setWidth("800px");
    }

    private void atualizarGrid() {
        try {
            List<CartaoDTO> cartoes = service.getCartoes();
            grid.setItems(cartoes);
        } catch (Exception e) {
            log.error("Falha ao atualizar o grid de cartões", e);
            grid.setItems(List.of());
        }
    }

    private void abrirModalCadastro() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Cadastrar Cartão");

        TextField txtNumero = new TextField("Número");
        TextField txtSenha = new TextField("Senha");
        TextField txtSaldo = new TextField("Saldo");

        Button btnConfirmar = new Button("Salvar", e -> {
            try {
                CartaoDTO dto = new CartaoDTO(txtNumero.getValue(), txtSenha.getValue(), txtSaldo.getValue());
                service.cadastraCartao(dto);

                Notification.show("Cartão cadastrado com sucesso!");
                atualizarGrid();
                dialog.close();
            } catch (Exception ex) {
                Notification.show("Erro ao cadastrar: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        btnConfirmar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.add(new VerticalLayout(txtNumero, txtSenha));
        dialog.getFooter().add(new Button("Cancelar", i -> dialog.close()), btnConfirmar);
        dialog.open();
    }

    private void abrirModalTransacao() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nova Transação");

        TextField txtNumero = new TextField("Número do Cartão");
        TextField txtSenha = new TextField("Senha");
        TextField txtValor = new TextField("Valor");

        Button btnProcessar = new Button("Autorizar", e -> {
            try {
                BigDecimal valor = BigDecimal.valueOf(Long.parseLong(txtValor.getValue()));
                Transacao t = new Transacao(new CardNumber(txtNumero.getValue()), new Senha(txtSenha.getValue()), valor);

                String resultado = service.realizaTransacao(t);

                Notification.show("Status da Transação: " + resultado, 5000, Notification.Position.TOP_CENTER);

                atualizarGrid();
                dialog.close();
            } catch (NumberFormatException nfe) {
                Notification.show("Valor inválido inserido.");
            } catch (Exception ex) {
                Notification.show("Erro no processamento: " + ex.getMessage());
            }
        });
        btnProcessar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        dialog.add(new VerticalLayout(txtNumero, txtSenha, txtValor));
        dialog.getFooter().add(new Button("Voltar", i -> dialog.close()), btnProcessar);
        dialog.open();
    }
}