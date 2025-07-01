package gui.Jogo;

import modelos.Jogo.Jogo;
import modelos.Jogo.Pote;
import modelos.Jogo.Vida;
import modelos.Jogo.VidaListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.net.URL;

public class VidaGUI extends JPanel implements VidaListener{
    private final JLabel labelVida;
    private int largura;
    private int altura;
    private final int alturaUnidade = 72;
    private final int larguraUnidade = 53;

    private Jogo jogo;
    private Vida vida;
    private final Pote pote;
    private JLabel[] vidaDiscreta;
    private JLabel labelTexto;
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

    public VidaGUI(Vida vida, int tipoJogador, Pote pote, Jogo jogo) {
        this.vida = vida;
        this.jogo = jogo;
        this.pote = pote;
        this.tipoJogador = tipoJogador;
        vida.adicionarListener(this);
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
                    largura - larguraUnidade - 27 * i - 4,
                    4,
                    larguraUnidade,
                    alturaUnidade
            );

            if (tipoJogador == 1) { // Jogador humano
                if (i >= vidaTotal - vida.getVida() / 100 ) {
                    vidaDiscreta[i].addMouseListener(listeners[i]); // Adiciona listeners somente para o player
                    carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
                }
            } else { // Inimigo
                carregarImagem(vidaDiscreta[i], caminhoVidaInimigo);
            }

            labelVida.add(vidaDiscreta[i]);
        }
    }


    // Refaz a vida com o valor selecionado toda a vez que chamarem um valor da vida
    @Override
    public void vidaSelecionada(int quantidadeTotalApostada) {
        int vidaAtual = vida.getVida();
        int vidaPerdida = 1500 - vidaAtual;
        int vidaDisponivel = vidaAtual;

        int unidadesPerdidas = vidaPerdida / 100;
        int unidadesApostadas = quantidadeTotalApostada / 100;
        int unidadesDisponiveis = vidaDisponivel / 100;

        System.out.println("[DEBUG] Renderização - " +
                "Perdidas: " + unidadesPerdidas + " | " +
                "Apostadas: " + unidadesApostadas + " | " +
                "Disponíveis: " + unidadesDisponiveis);

        for (int i = 0; i < vidaTotal; i++) {
            String caminho = null;

            if (i < unidadesPerdidas) {
                // Já perdidos (vazios) - primeiros índices
                caminho = null;
            }
            else if (i < unidadesPerdidas + unidadesApostadas) {
                // Selecionados (transparentes) - índices intermediários
                caminho = (tipoJogador == 1) ?
                        caminhoVidaJogadorTransparente :
                        caminhoVidaInimigoTransparente;
            }
            else if (i < unidadesPerdidas + unidadesApostadas + unidadesDisponiveis) {
                // Disponíveis (normais) - últimos índices
                caminho = (tipoJogador == 1) ?
                        caminhoVidaJogador :
                        caminhoVidaInimigo;
            }

            carregarImagem(vidaDiscreta[i], caminho);
        }
    }
    // Refaz a vida em final de round
    @Override
    public void vidaAlterada() {

        for (int i = 0; i < vidaTotal; i++) {
            // Remove listeners antigos, se houver
            for (MouseListener ml : vidaDiscreta[i].getMouseListeners()) {
                vidaDiscreta[i].removeMouseListener(ml);
            }

            if (tipoJogador == 1) {
                if (i >= vidaTotal - (vida.getVida() / 100 )) {
                    carregarImagem(vidaDiscreta[i], caminhoVidaJogador);

                    // Recria o listener
                    listeners[i] = new Listener(i);
                    vidaDiscreta[i].addMouseListener(listeners[i]);
                    listeners[i].funcional = true;
                } else {
                    carregarImagem(vidaDiscreta[i], null);
                    listeners[i] = null; // opcional: remover completamente
                }
            } else {
                if (i >= vidaTotal - (vida.getVida() / 100 )) {
                    carregarImagem(vidaDiscreta[i], caminhoVidaInimigo);
                } else {
                    carregarImagem(vidaDiscreta[i], null);
                }
            }
        }

        resetSelecao(); // garantir que o estado volte ao inicial
    }

    private class Listener implements MouseListener {
        int index;
        boolean funcional = true;

        public Listener(int index) {
            this.index = index;
        }


        @Override
        public void mouseEntered(MouseEvent e) {
            if (vidaSelecionado) return;
            for (int i = vidaTotal - vida.getVida() / 100; i <= index && listeners[i].funcional; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorPressionada);
            }
            if (index != vidaTotal - 1 && vidaTotal - vida.getVida() / 100 <= index) {
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogadorPressionada);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (vidaSelecionado) return;
            for (int i = vidaTotal - vida.getVida() / 100; i <= index && listeners[i].funcional; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            }
            if (index != vidaTotal - 1 && vidaTotal - vida.getVida() / 100 <= index) {
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogador);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int inicio = vidaTotal - vida.getVida() / 100;
            for (int i = inicio; i <= index; i++) {
                listeners[i].funcional = false;
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorTransparente);
            }

            vidaSelecionada = index - inicio + 1;
            vidaSelecionado = true;
            apostar(vidaSelecionada);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
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
            if (pote.getQuantidade() > 0 && valorAposta == pote.getQuantidade()) {
                jogo.getJogador().escolhaDaJogada(1); // Call
                jogo.registrarEscolhaJogador(1);
            } else {
                jogo.getJogador().escolhaDaJogada(2); // Raise
                jogo.registrarEscolhaJogador(2, valorAposta);
            }
        }

        resetSelecao();
    }



    private void resetSelecao() {
        vidaSelecionado = false;
        vidaSelecionada = 0;
    }


    // Getters
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
}