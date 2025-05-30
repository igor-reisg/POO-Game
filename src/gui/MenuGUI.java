package gui;

import modelos.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import java.net.*;
import java.util.Objects;

public class MenuGUI extends JPanel implements ActionListener {
    JanelaGUI app;
    String caminhoBackground = "/assets/images/background/pattern1.png";
    String caminhoBotoes = "/assets/images/botoes/menu/";
    JButton[] BotoesInferiores;
    ImageIcon[][] ImagensInferiores;
    JButton[] BotoesSuperiores;
    ImageIcon[][] ImagensSuperiores;

    public MenuGUI(JanelaGUI app) {
        this.app = app;

        //CRIAÇÃO BOTÕES INFERIORES
        BotoesInferiores = new JButton[3];
        ImagensInferiores = new ImageIcon[3][3];

        for (int i = 0; i < 3; i++) {
            BotoesInferiores[i] = criarBotao(caminhoBotoes + "inferior", i, 200, 80, ImagensInferiores);
        }

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panelInferior.add(BotoesInferiores[0]);
        panelInferior.add(BotoesInferiores[1]);
        panelInferior.add(BotoesInferiores[2]);

        //CRIAÇÃO BOTÕES SUPERIORES
        BotoesSuperiores = new JButton[3];
        ImagensSuperiores = new ImageIcon[2][3];

        for (int i = 0; i < 2; i++) {
            BotoesSuperiores[i] = criarBotao(caminhoBotoes + "superior", i, 70, 70, ImagensSuperiores);
        }

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        panelSuperior.add(BotoesSuperiores[0]);
        panelSuperior.add(BotoesSuperiores[1]);

        //Panel menu
        JPanel menuPainel = new JPanel(new BorderLayout());
        menuPainel.add(panelInferior, BorderLayout.SOUTH);
        menuPainel.add(panelSuperior, BorderLayout.NORTH);

        //PARTE DAS CARTAS PARA TESTAR NO MENU
        JPanel painelCartas = new JPanel();
        painelCartas.setLayout(new FlowLayout());
        Carta carta = new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS);
        CartasPanel cartaPainel = new CartasPanel(carta);
        painelCartas.add(cartaPainel);
        menuPainel.add(painelCartas, BorderLayout.CENTER);

        //Deixa tudo invisivel alem dos botoes/carta teste, pra mostrar o background
        panelInferior.setOpaque(false);
        panelSuperior.setOpaque(false);
        painelCartas.setOpaque(false);
        menuPainel.setOpaque(false);

        //Tem que adicionar
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(menuPainel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton criarBotao(String caminhoBase, int i, int largura, int altura, ImageIcon[][] matrizImagens) {
        for (int j = 0; j < 3; j++) {
            Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource(caminhoBase + i + "_" + j + ".png"))).getImage();
            img = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            matrizImagens[i][j] = new ImageIcon(img);
        }

        JButton botao = new JButton(matrizImagens[i][0]);
        botao.setPreferredSize(new Dimension(largura, altura));
        botao.setRolloverIcon(matrizImagens[i][1]);
        botao.setPressedIcon(matrizImagens[i][2]);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.addActionListener(this);

        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BotoesInferiores[0]) {
            app.trocarTela(new JogoGUI(app));
        }
        else if (e.getSource() == BotoesInferiores[1]) {
            System.out.println("opcoes");
        }
        else if (e.getSource() == BotoesInferiores[2]) {
            System.out.println("Creditos");
        }
        else if (e.getSource() == BotoesSuperiores[0]) {
            System.out.println("Ajuda");
        }
        else if (e.getSource() == BotoesSuperiores[1]) {
            System.exit(0);
        }
    }
}

