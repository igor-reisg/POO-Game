package gui;

import java.awt.*;
import java.util.Objects;

import javax.swing.*;

import modelos.Carta;

public class JogoGUI extends JPanel {
    JanelaGUI app;
    MesaGUI mesa;
    CartasPanel cartasJogador[], cartasInimigo[];
    IconeGUI jogadorIcon, adversarioIcon;
    VidaGUI jogadorHP, adversarioHP;
    JButton pause, inicio;
    JButton fold, check;
    JLabel pote;
    String caminhoBase = "/assets/images/botoes/jogo/";
    ImageIcon[] checkIcons, foldIcons;

    
    public JogoGUI(JanelaGUI app) {
        this.app = app;

        setLayout(new BorderLayout()); 

        //Background
        BackgroundPanel background = new BackgroundPanel("/assets/images/background/pattern1.png");
        background.setLayout(new BorderLayout(10, 20)); 
        add(background, BorderLayout.CENTER); 

        //Mesa
        mesa = new MesaGUI();
        mesa.setOpaque(false); 
        
        //Hub do jogador
        JPanel hubJogador = new JPanel();
        hubJogador.setLayout(new BoxLayout(hubJogador, BoxLayout.X_AXIS));
        //Icones
        jogadorIcon = new IconeGUI();
        jogadorIcon.setPreferredSize(new Dimension(200, 150));
        hubJogador.add(jogadorIcon);
        //Cartas do jogador
        JPanel cartasPanel = new JPanel(new FlowLayout());
        cartasJogador = new CartasPanel[2];
        cartasJogador[0] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));
        cartasJogador[1] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));

        cartasPanel.add(cartasJogador[0]);
        cartasPanel.add(cartasJogador[1]);
        cartasPanel.setOpaque(true);
        hubJogador.add(cartasPanel);
        hubJogador.add(Box.createHorizontalStrut(700));

        // Painel dos botões Check e Fold
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        check = new JButton();
        fold = new JButton();
        checkIcons = new ImageIcon[3];
        foldIcons = new ImageIcon[3];
        
        check = criarBotao(caminhoBase + "botaocheck",  200, 80, checkIcons);
        fold = criarBotao(caminhoBase + "botaofold", 200, 80, foldIcons);
        painelBotoes.add(check);
        painelBotoes.add(fold);

        // Painel com informações de vida e botões
        JPanel painelBotoesVida = new JPanel();
        painelBotoesVida.setLayout(new BoxLayout(painelBotoesVida, BoxLayout.Y_AXIS));

        jogadorHP = new VidaGUI();
        jogadorHP.setPreferredSize(new Dimension(1000, 50));
        jogadorHP.setMaximumSize(new Dimension(200, 50));
        jogadorHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        jogadorHP.setOpaque(true);
        jogadorHP.setBackground(Color.GREEN);

        painelBotoesVida.add(jogadorHP);
        painelBotoesVida.add(Box.createVerticalStrut(10));  
        painelBotoesVida.add(painelBotoes);

        hubJogador.add(painelBotoesVida);

                
        painelBotoesVida.setOpaque(false);
        painelBotoes.setOpaque(false);
        
        pote = new JLabel();
        pote.setPreferredSize(new Dimension(20, 100));
        pote.setBackground(Color.RED);
        pote.setOpaque(true);
        
        //Criação do Hub do Adversário
        JPanel hubAdversario = new JPanel();
        hubAdversario.setLayout(new BoxLayout(hubAdversario, BoxLayout.X_AXIS));
        
        //Vida do inimigo
        adversarioHP = new VidaGUI();
        adversarioHP.setPreferredSize(new Dimension(1000, 50));
        adversarioHP.setMaximumSize(new Dimension(200, 150));
        adversarioHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        adversarioHP.setOpaque(true);
        adversarioHP.setBackground(Color.RED);
        hubAdversario.add(adversarioHP);
        hubAdversario.add(Box.createHorizontalStrut(700));
        //Cartas do adversário
        JPanel cartasAdversarioPanel = new JPanel();
        cartasInimigo = new CartasPanel[2];
        cartasInimigo[0] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));
        cartasInimigo[1] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));
        cartasAdversarioPanel.add(cartasInimigo[0]);
        cartasAdversarioPanel.add(cartasInimigo[1]);
        cartasAdversarioPanel.setOpaque(false);
        hubAdversario.add(cartasAdversarioPanel);
        //Icone do adversario
        adversarioIcon = new IconeGUI();
        adversarioIcon.setPreferredSize(new Dimension(200, 200));
        hubAdversario.add(adversarioIcon);

        //Controle do Jogo(pause e home)
        JPanel controleJogo = new JPanel();
        controleJogo.setLayout(new BoxLayout(controleJogo, BoxLayout.Y_AXIS));
        
        //Set de todos os containers para falso para o background
        controleJogo.setOpaque(false);
        hubAdversario.setOpaque(false);
        hubJogador.setOpaque(false);
        cartasPanel.setOpaque(false);
        painelBotoes.setOpaque(false);
        painelBotoesVida.setOpaque(false);
        
        background.add(controleJogo, BorderLayout.EAST);
        background.add(hubAdversario, BorderLayout.NORTH);
        background.add(pote, BorderLayout.WEST);
        background.add(mesa, BorderLayout.CENTER);
        background.add(hubJogador, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String caminhoBase, int largura, int altura, ImageIcon[] matrizImagens) {
        for (int i = 0; i < 3; i++) {
            Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource(caminhoBase + i + ".png"))).getImage();
            img = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            matrizImagens[i] = new ImageIcon(img);
        }

        JButton botao = new JButton(matrizImagens[0]);
        botao.setPreferredSize(new Dimension(largura, altura));
        botao.setRolloverIcon(matrizImagens[1]);
        botao.setPressedIcon(matrizImagens[2]);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        //botao.addActionListener(this);

        return botao;
    }



}
