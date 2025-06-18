package gui.Jogo;

import java.awt.*;
import javax.swing.*;

import gui.*;
import gui.Loja.LojaGUI;
import gui.Menu.MenuGUI;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;

public class JogoGUI extends JPanel {
    private final JanelaGUI app;
    private final Jogo jogo;
    private final Dimension tamanhoTela;

    private final MesaGUI mesa;
    private final CartasPanel[] cartasJogador, cartasInimigo;
    private final IconeGUI jogadorIcon, inimigoIcon;
    private final VidaGUI jogadorHP, inimigoHP;
    private final BotoesGUI pause, inicio, check, fold;
    private final Inventario inventario;

    private final BackgroundPanel background;

    public JogoGUI(JanelaGUI app, Jogo jogo) {
        this.app = app;
        this.jogo = jogo;
        this.tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        setLayout(new BorderLayout());

        // Background
        background = new BackgroundPanel("/assets/images/background/pattern1.png");
        background.setLayout(null);
        add(background, BorderLayout.CENTER);

        // Ícone do jogador
        jogadorIcon = new IconeGUI("/assets/images/frames/framesBoss/boss0_1.png", "Gabiel Maka");
        Dimension jogadorIconSize = jogadorIcon.getPreferredSize();
        jogadorIcon.setBounds(0, tamanhoTela.height - jogadorIconSize.height, jogadorIconSize.width, jogadorIconSize.height);
        background.add(jogadorIcon);

        // Ícone do adversário
        inimigoIcon = new IconeGUI("/assets/images/frames/framesBoss/boss1_1.png", "Nulio Cisar");
        Dimension inimigoIconSize = inimigoIcon.getPreferredSize();
        inimigoIcon.setBounds(tamanhoTela.width - inimigoIconSize.width, 0, inimigoIconSize.width, inimigoIconSize.height);
        background.add(inimigoIcon);

        // Cartas do jogador
        cartasJogador = new CartasPanel[2];
        for (int i = 0; i < 2; i++) {
            cartasJogador[i] = new CartasPanel(jogo.getJogador().getMao()[i]);
            cartasJogador[i].setBounds(
                    jogadorIcon.getWidth() + cartasJogador[i].getWidth() * (1 + i) + 20,
                    tamanhoTela.height - cartasJogador[i].getHeight(),
                    cartasJogador[i].getWidth(),
                    cartasJogador[i].getHeight()
            );
            background.add(cartasJogador[i]);
        }

        cartasInimigo = new CartasPanel[2];
        for (int i = 0; i < 2; i++) {
            cartasInimigo[i] = new CartasPanel(jogo.getInimigo().getMao()[i]);
            cartasInimigo[i].setBounds(
                    tamanhoTela.width - inimigoIcon.getWidth() - cartasInimigo[i].getWidth() * (1 + i) - 130,
                    0,
                    cartasInimigo[i].getWidth(),
                    cartasInimigo[i].getHeight()
            );
            background.add(cartasInimigo[i]);
        }

        // Mesa
        mesa = new MesaGUI(jogo.getMesa());
        Dimension mesaSize = mesa.getPreferredSize();
        mesa.setBounds(
                (tamanhoTela.width - mesaSize.width) / 2,
                (tamanhoTela.height - mesaSize.height) / 2,
                mesaSize.width,
                mesaSize.height
        );
        mesa.setOpaque(false);
        background.add(mesa);

        // Vida do jogador
        jogadorHP = new VidaGUI(jogo.getJogador().getVida(), 1);
        Dimension vidaJogadorSize = jogadorHP.getPreferredSize();
        jogadorHP.setBounds(
                tamanhoTela.width - vidaJogadorSize.width,
                tamanhoTela.height - vidaJogadorSize.height - 100,
                vidaJogadorSize.width,
                vidaJogadorSize.height
        );
        background.add(jogadorHP);

        // Vida do adversário
        inimigoHP = new VidaGUI(jogo.getJogador().getVida(), 2);
        Dimension vidaAdversarioSize = inimigoHP.getPreferredSize();
        inimigoHP.setBounds(0, 0, vidaAdversarioSize.width, vidaAdversarioSize.height);
        background.add(inimigoHP);

        // Botão Pause
        pause = new BotoesGUI("jogo/pause", 50, 50, 0);
        pause.setBotao(pause.getBotao());
        pause.add(pause.getBotao());
        pause.setBounds(tamanhoTela.width - 50, (tamanhoTela.height - 50) / 2 - 50, 50, 50);
        pause.setOpaque(false);
        background.add(pause);

        inventario = new Inventario();
        pause.getBotao().addActionListener(e -> app.trocarTela(new LojaGUI(app, inventario)));

        // Botão Início
        inicio = new BotoesGUI("jogo/pause", 50, 50, 0);
        inicio.setBotao(inicio.getBotao());
        inicio.add(inicio.getBotao());
        inicio.setBounds(tamanhoTela.width - 50, (tamanhoTela.height - 50) / 2, 50, 50);
        inicio.setOpaque(false);
        background.add(inicio);

        inicio.getBotao().addActionListener(e -> app.trocarTela(new MenuGUI(app)));

        // Botão Check
        check = new BotoesGUI("jogo/check", 84, 42, 0);
        check.setEscalaX(5);
        check.setEscalaY(1.3);
        check.setBotao(check.getBotao());
        check.add(check.getBotao());
        check.setBounds(
                tamanhoTela.width - vidaJogadorSize.width,
                (int) (tamanhoTela.height - 84 * 1.3),
                42 * 5,
                (int) (84 * 1.3)
        );
        check.setOpaque(false);
        background.add(check);

        check.getBotao().addActionListener(e -> {
            jogadorHP.apostar();
            jogo.getJogador().escolhaDaJogada(1);
            jogo.registrarEscolhaJogador(1);
        });

        // Botão Fold
        fold = new BotoesGUI("jogo/fold", 84, 42, 0);
        fold.setEscalaX(5);
        fold.setEscalaY(1.3);
        fold.setBotao(fold.getBotao());
        fold.add(fold.getBotao());
        fold.setOpaque(false);
        fold.setBounds(
                tamanhoTela.width - vidaJogadorSize.width + (int) (42 * 5.5),
                (int) (tamanhoTela.height - 84 * 1.3),
                42 * 5,
                (int) (84 * 1.3)
        );
        background.add(fold);

        fold.getBotao().addActionListener(e -> {
            jogo.getJogador().escolhaDaJogada(0);
            jogo.registrarEscolhaJogador(0);
        });

        // Atualização automática a cada nova rodada
        jogo.setOnNovaRodada(() -> SwingUtilities.invokeLater(() -> atualizarTudo()));
    }

    public void atualizarTudo() {
        // Remove as cartas antigas
        for (CartasPanel cartaPanel : cartasJogador) {
            if (cartaPanel.getParent() == background) {
                background.remove(cartaPanel);
            }
        }

        for (CartasPanel cartaPanel : cartasInimigo) {
            if (cartaPanel.getParent() == background) {
                background.remove(cartaPanel);
            }
        }

        // Adiciona as novas cartas do jogador
        for (int i = 0; i < 2; i++) {
            cartasJogador[i] = new CartasPanel(jogo.getJogador().getMao()[i]);
            cartasJogador[i].setBounds(
                    jogadorIcon.getWidth() + cartasJogador[i].getWidth() * (1 + i) + 20,
                    tamanhoTela.height - cartasJogador[i].getHeight(),
                    cartasJogador[i].getWidth(),
                    cartasJogador[i].getHeight()
            );
            background.add(cartasJogador[i]);
        }

        for (int i = 0; i < 2; i++) {
            cartasInimigo[i] = new CartasPanel(jogo.getInimigo().getMao()[i]);
            cartasInimigo[i].setBounds(
                    tamanhoTela.width - inimigoIcon.getWidth() - cartasInimigo[i].getWidth() * (1 + i) - 130,
                    0,
                    cartasInimigo[i].getWidth(),
                    cartasInimigo[i].getHeight()
            );
            background.add(cartasInimigo[i]);
        }

        mesa.atualizarMesa(jogo.getMesa());

        this.revalidate();
        this.repaint();
    }
}
