package gui;

import java.awt.*;
import javax.swing.*;

import modelos.Carta;

public class JogoGUI extends JPanel {
    JanelaGUI app;
    MesaGUI mesa;
    CartasPanel cartasJogador[], cartasInimigo[];
    JLabel jogadorIcon, adversarioIcon;
    JLabel jogadorHP, adversarioHP;
    JButton pause, inicio;
    JButton fold, check;
    JLabel pote;

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
        jogadorIcon = new JLabel();
        jogadorIcon.setPreferredSize(new Dimension(200, 200));
        jogadorIcon.setBackground(Color.black);
        jogadorIcon.setOpaque(true);
        hubJogador.add(jogadorIcon);
        //Cartas
        JPanel cartasPanel = new JPanel(new FlowLayout());
        cartasJogador = new CartasPanel[2];
        cartasJogador[0] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));
        cartasJogador[1] = new CartasPanel(new Carta(Carta.Naipe.COPAS, Carta.Valor.DOIS));

        cartasPanel.add(cartasJogador[0]);
        cartasPanel.add(cartasJogador[1]);
        cartasPanel.setOpaque(true);
        hubJogador.add(cartasPanel);
        
        JPanel painelBotoes = new JPanel(new BorderLayout());
        check = new JButton("Check");
        check.setPreferredSize(new Dimension(200, 200));
        fold = new JButton("Fold");
        check.setPreferredSize(new Dimension(200, 200));
        painelBotoes.add(check, BorderLayout.WEST);
        painelBotoes.add(fold, BorderLayout.EAST);
        
        JPanel painelBotoesVida = new JPanel(new BorderLayout());
        jogadorHP = new JLabel("HP: 100");
        jogadorHP.setOpaque(true);
        jogadorHP.setBackground(Color.RED);
        painelBotoesVida.add(jogadorHP, BorderLayout.NORTH);
        painelBotoesVida.add(painelBotoes, BorderLayout.SOUTH);
        
        
        
        painelBotoesVida.setOpaque(false);
        painelBotoes.setOpaque(false);
        
        pote = new JLabel();
        pote.setPreferredSize(new Dimension(20, 100));
        pote.setBackground(Color.RED);
        pote.setOpaque(true);
        
        
        
        
        
        
        
        background.add(pote, BorderLayout.WEST);
        background.add(mesa, BorderLayout.CENTER);
        background.add(hubJogador, BorderLayout.SOUTH);
    }
}
