package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;
import javax.swing.text.StyledEditorKit.BoldAction;

import modelos.Carta;

public class JogoGUI extends JPanel {
    Dimension tamanhoTela;
    Double x_gap = 0.33;

    JanelaGUI app;
    MesaGUI mesa;
    CartasPanel cartasJogador[], cartasInimigo[];
    IconeGUI jogadorIcon, adversarioIcon;
    VidaGUI jogadorHP, adversarioHP;
    BotoesGUI pause, inicio;
    BotoesGUI fold, check;
    JLabel pote;
    ImageIcon[] checkIcons, foldIcons, pauseIcons, inicioIcons;

    
    public JogoGUI(JanelaGUI app) {
        this.app = app;
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        this.tamanhoTela = tamanhoTela;
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
        hubJogador.add(Box.createHorizontalStrut((int) (tamanhoTela.getWidth() * x_gap)));
 
        // Painel dos botões Check e Fold
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        
        check = new BotoesGUI("jogo/check", 63, 128, 0);
        fold = new BotoesGUI("jogo/fold", 63, 128, 0);


        checkIcons = new ImageIcon[3];
        foldIcons = new ImageIcon[3];
        

        painelBotoes.add(check.getBotao());
        painelBotoes.add(fold.getBotao());

        // Painel com informações de vida e botões
        JPanel painelBotoesVida = new JPanel();
        painelBotoesVida.setLayout(new BoxLayout(painelBotoesVida, BoxLayout.Y_AXIS));

        jogadorHP = new VidaGUI();
        jogadorHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        jogadorHP.setOpaque(true);
        jogadorHP.setBackground(Color.GREEN);

        painelBotoesVida.add(jogadorHP);
        painelBotoesVida.add(painelBotoes);

        hubJogador.add(painelBotoesVida);

        painelBotoesVida.setOpaque(false);
        painelBotoes.setOpaque(false);
        
        JPanel potePanel = new JPanel();
        potePanel.setLayout(new FlowLayout());
        pote = new JLabel();
        //pote.setPreferredSize(new Dimension(20, 30));
        pote.setBackground(Color.RED);
        potePanel.add(pote);
        pote.setOpaque(true);
        
        //Criação do Hub do Adversário
        JPanel hubAdversario = new JPanel();
        hubAdversario.setLayout(new BoxLayout(hubAdversario, BoxLayout.X_AXIS));
        
        //Vida do inimigo

        adversarioHP = new VidaGUI();
        adversarioHP.setLayout(new BorderLayout(20, 20));
        adversarioHP.setPreferredSize(new Dimension(1000, 10));
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
        //adversarioIcon.setPreferredSize(new Dimension(200, 200));
        hubAdversario.add(adversarioIcon);

        //Controle do Jogo(pause e home)
        pauseIcons = new ImageIcon[3];
        JPanel controleJogo = new JPanel();
        controleJogo.setLayout(new BoxLayout(controleJogo, BoxLayout.Y_AXIS));
        pause = new BotoesGUI("jogo/pause", 84, 42, 0);
        inicio = new BotoesGUI("jogo/pause", 84, 42, 0);

        inicio.getBotao().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                app.trocarTela(new MenuGUI(app));
            }
        });

        controleJogo.add(pause.getBotao());
        controleJogo.add(inicio.getBotao());
        controleJogo.setOpaque(true);

        //Set de todos os containers para falso para o background
        controleJogo.setOpaque(false);
        hubAdversario.setOpaque(false);
        hubJogador.setOpaque(false);
        cartasPanel.setOpaque(false);
        painelBotoes.setOpaque(false);
        painelBotoesVida.setOpaque(false);
        
        background.add(controleJogo, BorderLayout.EAST);
        background.add(hubAdversario, BorderLayout.NORTH);
        background.add(potePanel, BorderLayout.WEST);
        background.add(mesa, BorderLayout.CENTER);
        background.add(hubJogador, BorderLayout.SOUTH);
    }

}
