package gui.Menu;

import gui.*;
import gui.Jogo.*;
import modelos.Jogo.CriarPersona;
import modelos.Jogo.Jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuGUI extends JPanel implements ActionListener {
    private final JanelaGUI app;
    private final String caminhoBackground = "/assets/images/background/pattern2.png";
    private BotoesGUI[] BotoesMenu;
    private CriarPersona CriarPersona;
    private CreditosGUI creditosGUI;

    public MenuGUI(JanelaGUI app) {
        this.app = app;

        this.creditosGUI = null;

        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Criação do logo
        LogoMenu logo = new LogoMenu();
        logo.setPreferredSize(new Dimension(1100, 800));
        logo.setLayout(null);
        logo.setOpaque(false);

        // Criação dos botões
        BotoesMenu = new BotoesGUI[5];
        for (int i = 0; i < BotoesMenu.length; i++) {
            BotoesMenu[i] = new BotoesGUI("menu/", i < 3 ? 95 : 66, i < 3 ? 189 : 66, i);
            BotoesMenu[i].getBotao().addActionListener(this);
        }

        // Painel inferior (botões principais)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panelInferior.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            panelInferior.add(BotoesMenu[i].getBotao());
        }

        // Painel superior (botões auxiliares)
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        panelSuperior.setOpaque(false);
        for (int i = 3; i < 5; i++) {
            panelSuperior.add(BotoesMenu[i].getBotao());
        }

        // Painel principal do menu
        JPanel menuPainel = new JPanel(new BorderLayout());
        menuPainel.setOpaque(false);
        menuPainel.add(panelSuperior, BorderLayout.NORTH);
        menuPainel.add(logo, BorderLayout.CENTER);
        menuPainel.add(panelInferior, BorderLayout.SOUTH);

        // Background
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(menuPainel, BorderLayout.CENTER);

        add(background, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BotoesMenu[0].getBotao()) {
            if (CriarPersona == null) {
                CriarPersona = new CriarPersona(app);
            }
            app.trocarTela(CriarPersona);
        }
        else if (e.getSource() == BotoesMenu[1].getBotao()) {
            mostrarPainelOpcoes();
        }
        else if (e.getSource() == BotoesMenu[2].getBotao()) {
            if (creditosGUI == null) {
                creditosGUI = new CreditosGUI(app);
            }
            app.trocarTela(creditosGUI);
        }
        else if (e.getSource() == BotoesMenu[3].getBotao()) {
            JOptionPane.showMessageDialog(this, "Ajuda - Instruções do jogo");
        }
        else if (e.getSource() == BotoesMenu[4].getBotao()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair do jogo?", "Sair",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void mostrarPainelOpcoes() {
        StaticBackgroundPanel painelOpcoes = new StaticBackgroundPanel(caminhoBackground, 1280, 720);
        painelOpcoes.setLayout(new BorderLayout());

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> app.getGlassPane().setVisible(false));

        painelOpcoes.add(botaoVoltar, BorderLayout.SOUTH);

        TransparenteGUI transparente = new TransparenteGUI(painelOpcoes);
        app.setGlassPane(transparente);
        transparente.setVisible(true);
    }
}