package gui.Jogo;

import modelos.Jogo.Jogo;
import modelos.Jogo.Vida;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class VidaGUI extends JPanel {
    private final JLabel labelVida;
    private final PoteGUI poteGUI;
    private int largura;
    private int altura;
    private final int alturaUnidade = 72;
    private final int larguraUnidade = 53;

    private Jogo jogo;
    private Vida vida;
    private JLabel[] vidaDiscreta;
    private Listener[] listeners;
    private final String caminhoVidaInimigo = "/assets/images/JogoHUB/unidadevida3.png";
    private final String caminhoVidaJogador = "/assets/images/JogoHUB/unidadevida2.png";
    private final String caminhoVidaJogadorPressionada = "/assets/images/JogoHUB/unidadevida1.png";
    private final String caminhoVidaJogadorTransparente = "/assets/images/JogoHUB/unidadevida5.png";
    private final String caminhoVidaInimigoTransparente = "/assets/images/JogoHUB/unidadevida6.png";

    private final int vidaTotal = 15;
    private int vidaAtual;
    private int vidaSelecionada = 0;
    private boolean vidaSelecionado = false;
    private final int tipoJogador; // 1 para jogador, 2 para inimigo

    public VidaGUI(Vida vida, int tipoJogador, PoteGUI poteGUI, Jogo jogo) {
        this.vida = vida;
        this.jogo = jogo;
        this.poteGUI = poteGUI;
        this.tipoJogador = tipoJogador;
        vidaAtual = vida.getVida() / 100;
        labelVida = new JLabel();
        labelVida.setLayout(null);

        carregarImagem(labelVida, "/assets/images/JogoHUB/barravida1.png");
        setDimensions();
        setOpaque(false);
        add(labelVida);
        vidaDiscreta = new JLabel[vidaTotal];
        listeners = new Listener[vidaTotal];

        for (int i = 0; i < vidaTotal; i++) {
            vidaDiscreta[i] = new JLabel();
            listeners[i] = new Listener(i);

            vidaDiscreta[i].setBounds(
                    largura - larguraUnidade - 27 * i - 3,
                    4,
                    larguraUnidade,
                    alturaUnidade
            );

            if (tipoJogador == 1) { // Jogador humano
                if (i >= vidaTotal - vidaAtual) {
                    vidaDiscreta[i].addMouseListener(listeners[i]);
                    carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
                }
            } else { // Inimigo
                carregarImagem(vidaDiscreta[i], caminhoVidaInimigo);
            }

            labelVida.add(vidaDiscreta[i]);
        }
    }


    private class Listener implements MouseListener {
        int index;

        public Listener(int index) {
            this.index = index;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (tipoJogador != 1) return; // Só jogador humano pode clicar

            for(int i = vidaTotal - vidaAtual; i <= index; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorTransparente);
            }

            vidaSelecionada = index - (vidaTotal - vidaAtual) + 1;
            vidaSelecionado = true;
            apostar(vidaSelecionada);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(vidaSelecionado || tipoJogador != 1) return;

            for(int i = vidaTotal - vidaAtual; i <= index; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorPressionada);
            }
            if(index != vidaTotal - 1) {
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogadorPressionada);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(vidaSelecionado || tipoJogador != 1) return;

            for(int i = vidaTotal - vidaAtual; i <= index; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            }
            if(index != vidaTotal - 1) {
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogador);
            }
        }

        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mousePressed(MouseEvent e) {}
    }

    private void carregarImagem(JLabel label, String caminhoImagem) {
        try {
            if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
                label.setIcon(null);
                return;
            }

            URL urlImagem = getClass().getResource(caminhoImagem);
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                label.setIcon(imagem);
            } else {
                System.err.println("Imagem não encontrada: " + caminhoImagem);
                label.setIcon(null);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            label.setIcon(null);
        }
    }

    private void setDimensions() {
        try {
            URL urlImagem = getClass().getResource("/assets/images/JogoHUB/barravida1.png");
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                largura = img.getWidth(null);
                altura = img.getHeight(null);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
        }
    }

    private void apostar(int unidades) {
        int valorAposta = unidades * 100;

        if (tipoJogador == 1) {
            if (poteGUI.getPote().getQuantidade() > 0 && valorAposta == poteGUI.getPote().getQuantidade()) {
                jogo.getJogador().escolhaDaJogada(1); // Call
                jogo.registrarEscolhaJogador(1);
            } else {
                jogo.getJogador().escolhaDaJogada(2); // Raise
                jogo.registrarEscolhaJogador(2, valorAposta);
            }
        }

        poteGUI.adicionarPote(poteGUI.getPote().getQuantidade());
        confirmarAposta(unidades);
    }

    public void perderVida(int unidades) {
        unidades = Math.max(0, unidades);
        int inicio = vidaTotal - vidaAtual;
        int fim = Math.min(inicio + unidades, vidaTotal);

        for (int i = inicio; i < fim; i++) {
            if (vidaDiscreta[i] != null) {
                vidaDiscreta[i].removeMouseListener(listeners[i]);
                carregarImagem(vidaDiscreta[i], null);
            }
        }

        vidaAtual = Math.max(0, vidaAtual - unidades);
        vida.setVida(vidaAtual * 100);
        resetSelecao();
    }

    public void retornarVida(int unidades) {
        unidades = Math.max(0, unidades);
        int inicio = vidaTotal - vidaAtual - 1;
        int fim = Math.max(inicio - unidades, -1);

        for (int i = inicio; i > fim; i--) {
            if (vidaDiscreta[i] != null) {
                if (tipoJogador == 1) {
                    vidaDiscreta[i].addMouseListener(listeners[i]);
                }
                String imagem = (tipoJogador == 1) ? caminhoVidaJogador : caminhoVidaInimigo;
                carregarImagem(vidaDiscreta[i], imagem);
            }
        }

        vidaAtual = Math.min(vidaTotal, vidaAtual + unidades);
        vida.setVida(vidaAtual * 100);
        resetSelecao();
    }

    public void mostrarAposta(int unidades) {
        String imagemTransparente = (tipoJogador == 1) ?
                caminhoVidaJogadorTransparente : caminhoVidaInimigoTransparente;

        int inicio = vidaTotal - vidaAtual;
        int fim = inicio + unidades;

        for (int i = inicio; i < fim && i < vidaTotal; i++) {
            carregarImagem(vidaDiscreta[i], imagemTransparente);
            if (tipoJogador == 1) {
                vidaDiscreta[i].removeMouseListener(listeners[i]);
            }
        }
    }

    public void confirmarAposta(int unidades) {
        vidaAtual -= unidades;
        vida.setVida(vidaAtual * 100);
        resetSelecao();
    }

    private void resetSelecao() {
        vidaSelecionado = false;
        vidaSelecionada = 0;
    }

    // Getters
    public int getVidaSelecionada() { return vidaSelecionada; }
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public int getVidaAtual() { return vidaAtual; }
}