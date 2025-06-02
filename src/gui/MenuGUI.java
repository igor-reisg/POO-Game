package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Objects;

public class MenuGUI extends JPanel implements ActionListener {
    JanelaGUI app;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    BotoesGUI[] BotoesMenu;

    public MenuGUI(JanelaGUI app) {
        this.app = app;

        //CRIAÇÃO DOS BOTÕES DO MENU
        BotoesMenu = new BotoesGUI[5];
        for (int i = 0; i < BotoesMenu.length; i++) {
            if (i < 3)
                BotoesMenu[i] = new BotoesGUI("menu/", 126, 252, i);
            else
                BotoesMenu[i] = new BotoesGUI("menu/", 88, 88, i);
        }

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panelInferior.add(BotoesMenu[0].getBotao());
        BotoesMenu[0].getBotao().addActionListener(this);

        panelInferior.add(BotoesMenu[1].getBotao());
        BotoesMenu[1].getBotao().addActionListener(this);

        panelInferior.add(BotoesMenu[2].getBotao());
        BotoesMenu[2].getBotao().addActionListener(this);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        panelSuperior.add(BotoesMenu[3].getBotao());
        BotoesMenu[3].getBotao().addActionListener(this);

        panelSuperior.add(BotoesMenu[4].getBotao());
        BotoesMenu[4].getBotao().addActionListener(this);

        //Panel menu
        JPanel menuPainel = new JPanel(new BorderLayout());
        menuPainel.add(panelInferior, BorderLayout.SOUTH);
        menuPainel.add(panelSuperior, BorderLayout.NORTH);

        //Deixa tudo invisivel alem dos botoes/carta teste, pra mostrar o background
        panelInferior.setOpaque(false);
        panelSuperior.setOpaque(false);
        menuPainel.setOpaque(false);

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
            app.trocarTela(new JogoGUI(app));
        }
        else if (e.getSource() == BotoesMenu[1].getBotao()) {
            mostrarPainelOpcoes();
        }
        else if (e.getSource() == BotoesMenu[2].getBotao()) {
            System.out.println("Creditos");
        }
        else if (e.getSource() == BotoesMenu[3].getBotao()) {
            System.out.println("Ajuda");
        }
        else if (e.getSource() == BotoesMenu[4].getBotao()) {
            System.exit(0);
        }
    }
}