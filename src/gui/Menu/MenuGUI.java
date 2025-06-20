package gui.Menu;

import gui.*;
import gui.Jogo.*;
import modelos.Jogo.Jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuGUI extends JPanel implements ActionListener {
    JanelaGUI app;
    private final String caminhoBackground = "/assets/images/background/pattern2.png";
    BotoesGUI[] BotoesMenu;
    JogoGUI telaJogo;

    public MenuGUI(JanelaGUI app) {
        this.app = app;
        telaJogo =  new JogoGUI(app, new Jogo());
        //CRIAÇÃO DO LOGO DO MENU
        LogoMenu logo = new LogoMenu();
        logo.setPreferredSize(new Dimension(1100, 800));
        logo.setLayout(null);

        //CRIAÇÃO DOS BOTÕES DO MENU
        BotoesMenu = new BotoesGUI[5];
        for (int i = 0; i < BotoesMenu.length; i++) {
            if (i < 3)
                BotoesMenu[i] = new BotoesGUI("menu/", 95, 189, i);
            else
                BotoesMenu[i] = new BotoesGUI("menu/", 66, 66, i);
        }

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        for (int i = 0; i < 3; i++) {
            panelInferior.add(BotoesMenu[i].getBotao());
            BotoesMenu[i].getBotao().addActionListener(this);
        }

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        for (int i = 3; i < 5; i++) {
            panelSuperior.add(BotoesMenu[i].getBotao());
            BotoesMenu[i].getBotao().addActionListener(this);
        }

        //Panel menu
        JPanel menuPainel = new JPanel(new BorderLayout());
        menuPainel.add(panelInferior, BorderLayout.SOUTH);
        menuPainel.add(panelSuperior, BorderLayout.NORTH);
        menuPainel.add(logo, BorderLayout.CENTER);

        //Deixa tudo invisivel alem dos botoes/carta teste, pra mostrar o background
        panelInferior.setOpaque(false);
        panelSuperior.setOpaque(false);
        menuPainel.setOpaque(false);
        logo.setOpaque(false);

        //Tem que adicionar
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(menuPainel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
        setVisible(true);
    }

    private void mostrarPainelOpcoes() {
        StaticBackgroundPanel painelOpcoes = new StaticBackgroundPanel(caminhoBackground, 1280, 720);
        painelOpcoes.setLayout(new BorderLayout());

        JButton botaoVoltar = new JButton("Voltar");
        painelOpcoes.add(botaoVoltar, BorderLayout.SOUTH);
        botaoVoltar.addActionListener(e -> {
            app.getGlassPane().setVisible(false);
        });

        TransparenteGUI transparente = new TransparenteGUI(painelOpcoes);
        app.setGlassPane(transparente);
        transparente.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BotoesMenu[0].getBotao()) {
            app.trocarTela(telaJogo);
        }
        else if (e.getSource() == BotoesMenu[1].getBotao()) {
            mostrarPainelOpcoes();
        }
        else if (e.getSource() == BotoesMenu[2].getBotao()) {
            app.trocarTela(new CreditosGUI(app));
        }
        else if (e.getSource() == BotoesMenu[3].getBotao()) {
            System.out.println("Ajuda");
        }
        else if (e.getSource() == BotoesMenu[4].getBotao()) {
            System.exit(0);
        }
    }
}