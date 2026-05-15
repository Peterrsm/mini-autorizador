package com.pedromiranda.miniautorizador.views;

import com.pedromiranda.miniautorizador.entity.CardNumber;
import com.pedromiranda.miniautorizador.entity.Cartao;
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

import java.math.BigDecimal;

@Route("dashboard")
@AnonymousAllowed
public class DashboardView extends VerticalLayout {
    private final CartaoServiceImpl service;

    // Grid definido como atributo de classe para permitir atualização de estado (Refresh)
    private final Grid<Cartao> grid = new Grid<>(Cartao.class, false);

    public DashboardView(CartaoServiceImpl service) {
        this.service = service;

        // Configurações de layout da View principal
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        // Componentes são instanciados como objetos Java e adicionados à árvore de componentes
        add(new H1("Mini Autorizador - Painel de Controle"));

        // Barra de ferramentas contendo as ações principais
        add(criarToolbar());

        // Configuração técnica da tabela de dados
        configurarGrid();
        add(grid);

        // Carga inicial de dados ao instanciar a View
        atualizarGrid();
    }

    /**
     * Cria a barra de ações superior. No Vaadin, eventos de clique (e -> ...)
     * executam lógica Java diretamente no servidor.
     */
    private HorizontalLayout criarToolbar() {
        Button btnNovoCartao = new Button("Novo Cartão", e -> abrirModalCadastro());
        btnNovoCartao.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnNovaTransacao = new Button("Realizar Transação", e -> abrirModalTransacao());
        btnNovaTransacao.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        return new HorizontalLayout(btnNovoCartao, btnNovaTransacao);
    }

    private void configurarGrid() {
        // Mapeamento explícito das colunas para evitar exposição de dados sensíveis da Entity
        grid.addColumn(Cartao::getNumeroCartao).setHeader("Número do Cartão").setAutoWidth(true);
        grid.addColumn(Cartao::getSaldo).setHeader("Saldo Atual").setAutoWidth(true);

        grid.setHeight("400px");
        grid.setWidth("800px");
    }

    /**
     * Método responsável por sincronizar a UI com o estado atual do banco de dados.
     */
    private void atualizarGrid() {
        grid.setItems(service.getCartoes());
    }

    /**
     * Dialogs são modais que não exigem navegação de página.
     * Ideal para manter o contexto do usuário no Dashboard.
     */
    private void abrirModalCadastro() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Cadastrar Cartão");

        TextField txtNumero = new TextField("Número");
        TextField txtSenha = new TextField("Senha");

        Button btnConfirmar = new Button("Salvar", e -> {
            try {
                CartaoDTO dto = new CartaoDTO(txtNumero.getValue(), txtSenha.getValue());
                service.cadastraCartao(dto);

                Notification.show("Cartão cadastrado com sucesso!");
                atualizarGrid(); // Atualiza a tabela em tempo real sem F5
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

                // O retorno da String (ex: "OK", "SALDO_INSUFICIENTE") é exibido via Notification
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