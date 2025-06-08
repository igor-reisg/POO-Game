package gui.Jogo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import gui.*;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;
import gui.Loja.LojaGUI;
import gui.Menu.MenuGUI;
import modelos.Cartas.Carta;

public class JogoGUI extends JPanel {
    private Dimension tamanhoTela;

    JanelaGUI app;
    MesaGUI mesa;
    CartasPanel[] cartasJogador;
    CartasPanel cartasInimigo;
    IconeGUI jogadorIcon, adversarioIcon;
    VidaGUI jogadorHP, adversarioHP;
    BotoesGUI pause, inicio;
    BotoesGUI check, fold;
    ImageIcon[] checkIcons, foldIcons, pauseIcons, inicioIcons;
    Inventario inventario;

    public JogoGUI(JanelaGUI app, Jogo jogo) {
        this.app = app;

        tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = tamanhoTela.width;
        int alturaTela = tamanhoTela.height;

        setLayout(new BorderLayout());

        //Background
        BackgroundPanel background = new BackgroundPanel("/assets/images/background/pattern1.png");
        background.setLayout(null);
        add(background, BorderLayout.CENTER);

        //Icone do player
        jogadorIcon = new IconeGUI("/assets/images/frames/framesBoss/boss0_1.png", "Gabiel Maka");
        Dimension jogadorIconSize = jogadorIcon.getPreferredSize();
        jogadorIcon.setBounds(0, alturaTela - jogadorIconSize.height, jogadorIconSize.width, jogadorIconSize.height);
        background.add(jogadorIcon);

        //Icone do adversario
        adversarioIcon = new IconeGUI("/assets/images/frames/framesBoss/boss1_1.png", "Nulio Cisar");
        Dimension adversarioIconSize = adversarioIcon.getPreferredSize();
        adversarioIcon.setBounds(larguraTela - adversarioIconSize.width, 0, adversarioIconSize.width, adversarioIconSize.height);
        background.add(adversarioIcon);

        //Cartas do adversário
        cartasJogador = new CartasPanel[2];
        for(int i = 0 ; i < 2 ; i++){
            cartasJogador[i] = new CartasPanel(jogo.getJogador().getMao()[i]);
            cartasJogador[i].setBounds(
                    jogadorIcon.getWidth() + cartasJogador[i].getWidth()*(1+i) + 20,
                    alturaTela - cartasJogador[i].getHeight() ,
                    cartasJogador[i].getWidth(),
                    cartasJogador[i].getHeight());
            background.add(cartasJogador[i]);
        }

        //Mesa
        mesa = new MesaGUI(jogo.getMesa());
        Dimension mesaSize = mesa.getPreferredSize();
        mesa.setBounds( (int) (larguraTela/2 - ( mesaSize.getWidth()/2) ) , (int) (alturaTela/2 - mesaSize.getHeight()/2),  mesaSize.width, mesaSize.height);
        mesa.setOpaque(false);
        background.add(mesa);

        //Vida do jogador
        jogadorHP = new VidaGUI(jogo.getJogador().getVida(), 1);
        Dimension vidaJogadorSize = jogadorHP.getPreferredSize();
        jogadorHP.setBounds(
                larguraTela - vidaJogadorSize.width,
                alturaTela - vidaJogadorSize.height - 100,
                vidaJogadorSize.width,
                vidaJogadorSize.height
        );

        background.add(jogadorHP);

        //Vida do Adversario
        adversarioHP = new VidaGUI(jogo.getJogador().getVida(), 2);
        Dimension vidaAdversarioSize = adversarioHP.getPreferredSize();
        adversarioHP.setBounds(
                0,
                0,
                vidaAdversarioSize.width,
                vidaAdversarioSize.height
        );

        background.add(adversarioHP);

        //Pause
        pause = new BotoesGUI("jogo/pause", 50, 50, 0);
        pause.setBotao(pause.getBotao());
        pause.add(pause.getBotao());
        pauseIcons = new ImageIcon[3];
        pause.setBounds(larguraTela - 50, (alturaTela - 50)/2 - 50, 50, 50);
        pause.setOpaque(false);
        background.add(pause);
        inventario = new Inventario();
        pause.getBotao().addActionListener(e -> {
            app.trocarTela(new LojaGUI(app, inventario));
        });

        //Inicio
        inicio = new BotoesGUI("jogo/pause", 50, 50, 0);
        inicio.setBotao(inicio.getBotao());
        inicio.add(inicio.getBotao());
        inicioIcons = new ImageIcon[3];
        inicio.setBounds(larguraTela - 50, (alturaTela - 50)/2, 50, 50);
        inicio.setOpaque(false);
        background.add(inicio);
        inicio.getBotao().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                app.trocarTela(new MenuGUI(app));
            }
        });

        //Check
        check = new BotoesGUI("jogo/check", 84, 42, 0);
        check.setEscalaX(5);
        check.setEscalaY(1.3);
        checkIcons = new ImageIcon[3];
        check.setBotao(check.getBotao()); //KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        check.add(check.getBotao());
        check.setBounds(
                (larguraTela  - vidaJogadorSize.width),
                (int) (alturaTela - 84 * 1.3),
                (42 * 5),
                (int) (84 * 1.3)
        );
        check.setOpaque(false);
        background.add(check);
        check.getBotao().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                jogo.getJogador().escolhaDaJogada(1);
                jogo.registrarEscolhaJogador(1);
            }
        });

        //Fold
        fold = new BotoesGUI("jogo/fold", 84, 42, 0);
        foldIcons = new ImageIcon[3];
        fold.setEscalaX(5);
        fold.setEscalaY(1.3);
        fold.setBotao(fold.getBotao());
        fold.add(fold.getBotao());
        fold.setOpaque(false);
        background.add(fold);
        fold.setBounds(
                (int) (larguraTela  - vidaJogadorSize.width + 42 * 5.5),
                (int) (alturaTela - 84 * 1.3),
                (int)(42 * 5),
                (int) (84 * 1.3)
        );
        fold.getBotao().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.getJogador().escolhaDaJogada(0);
                jogo.registrarEscolhaJogador(0);
            }
        });
    }
}
