package gui.Jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class VidaGUI extends JPanel {
    private final JLabel labelVida;
    private JLabel labelVidaDiscreta;
    private int largura;
    private int altura;
    private final int alturaUnidade = 72;
    private final int larguraUnidade = 53;

    private int vida;
    private JLabel[] vidaDiscreta;
    private Listener[] listeners;
    private final String caminhoVidaInimigo = "/assets/images/JogoHUB/unidadevida3.png";
    private final String caminhoVidaJogador = "/assets/images/JogoHUB/unidadevida2.png";
    private final String caminhoVidaJogadorPressionada = "/assets/images/JogoHUB/unidadevida1.png";
    private final String caminhoVidaJogadorTransparente = "/assets/images/JogoHUB/unidadevida5.png";
    private final String caminhoVidaInimigoTransparente = "/assets/images/JogoHUB/unidadevida6.png";

    private final int vidaTotal = 15;
    private int vidaAtual = 15;
    private int vidaSelecionada = 0;

    public VidaGUI(int vida, int tipoJogador) {
        this.vida = vida;
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

            if (tipoJogador == 1) {
                if (i >= vidaTotal - vidaAtual) {
                    vidaDiscreta[i].addMouseListener(listeners[i]);
                    carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
                }
            } else {
                carregarImagem(vidaDiscreta[i], caminhoVidaInimigo);
            }

            labelVida.add(vidaDiscreta[i]);
        }
    }

    private class Listener implements MouseListener {
        int index;

        @Override
        public void mouseClicked(MouseEvent e) {
            for(int i = vidaTotal - vidaAtual ; i <= index ; i++){
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorTransparente);
            }
            vidaSelecionada = vidaAtual - (vidaTotal - index);
            System.out.println(vidaSelecionada);
            apostar();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            for (int i = vidaTotal - vidaAtual; i <= index; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorPressionada);
            }
            if(index < vidaTotal - 1){
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogadorPressionada);
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            for (int i = vidaTotal - vidaAtual ; i <= index  ; i++) {
                carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            }
            if(index < vidaTotal - 1){
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogador);
            }
        }

        public Listener(int index) {
            this.index = index;
        }
    }

    private void carregarImagem(JLabel label, String caminhoImagem) {
        try {
            URL urlImagem = getClass().getResource(caminhoImagem);
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                label.setIcon(imagem);
            } else {
                label.setIcon(null);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem na vida: " + e.getMessage());
        }
    }

    private void reabilitarUnidadesVida(int cura) {
        int novaVida = Math.min(vidaAtual + cura, vidaTotal);
        for (int i = vidaTotal - novaVida; i < vidaTotal - vidaAtual; i++) {
            carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            vidaDiscreta[i].addMouseListener(listeners[i]);
        }
        vidaAtual = novaVida;
    }

    private void desabilitarUnidadesVida(int dano) {
        int novaVida = Math.max(vidaAtual - dano, 0);
        for (int i = vidaTotal - vidaAtual; i < vidaTotal - novaVida; i++) {
            vidaDiscreta[i].removeMouseListener(listeners[i]);
        }
        vidaAtual = novaVida;
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
            System.out.println("Erro ao carregar imagem da vida: " + e.getMessage());
        }
    }

    public void vidaRetornada(int cura) {
        reabilitarUnidadesVida(cura);
    }

    public int apostar() {

        int aux = vidaSelecionada;
        desabilitarUnidadesVida(vidaSelecionada + 1);
        System.out.println(vidaSelecionada);
        vidaSelecionada = 0;
        return aux;
    }

    public void setVidaApostada(int vidaSelecionada) {
        this.vidaSelecionada = vidaSelecionada;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
