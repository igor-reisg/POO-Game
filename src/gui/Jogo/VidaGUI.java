package gui.Jogo;

import modelos.Jogo.Vida;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class VidaGUI extends JPanel {
    private final JLabel labelVida;
    private final PoteGUI poteGUI;
    private JLabel labelVidaDiscreta;
    private int largura;
    private int altura;
    private final int alturaUnidade = 72;
    private final int larguraUnidade = 53;

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

    public VidaGUI(Vida vida, int tipoJogador, PoteGUI poteGUI) {
        this.vida = vida;
        this.poteGUI = poteGUI;
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

        public Listener(int index) {
            this.index = index;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for(int i = vidaTotal - vidaAtual; i <= index ; i++ ){
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorTransparente);
            }
            vidaSelecionada = index - (vidaTotal - vidaAtual);
            vidaSelecionado = true;
            apostar(vidaSelecionada + 1);
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            if(vidaSelecionado){
                return;
            }
            for(int i = vidaTotal - vidaAtual ; i <= index ; i++){
                carregarImagem(vidaDiscreta[i], caminhoVidaJogadorPressionada);
            }
            if(index != vidaTotal - 1){
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogadorPressionada);
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(vidaSelecionado){
                return;
            }
            for(int i = vidaTotal - vidaAtual ; i <= index ; i++){
                carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            }
            if(index != vidaTotal - 1){
                carregarImagem(vidaDiscreta[index + 1], caminhoVidaJogador);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
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

    private void apostar(int valor) {
        poteGUI.getPote().adicionarQuantidade(valor);
        poteGUI.adicionarPote(poteGUI.getPote().getQuantidade());
        //Utilizada para te
    }
    public void perderVida(int valor) {
        int inicio = vidaTotal - vidaAtual; // índice da primeira "vida atual"
        int fim = inicio + valor;           // índice da última a ser removida

        for (int i = inicio; i < fim && i < vidaTotal; i++) {
            vidaDiscreta[i].removeMouseListener(listeners[i]);
            vidaDiscreta[i].setIcon(null);
        }

        vidaAtual = Math.max(0, vidaAtual - valor);
        vidaSelecionado = false;
        vidaSelecionada = 0;
    }

    public void retornarVida(int valor) {
        int inicio = vidaTotal - vidaAtual - 1;               // última vida perdida
        int fim = inicio - valor;                             // até onde deve retornar

        for (int i = inicio; i > fim && i >= 0; i--) {
            carregarImagem(vidaDiscreta[i], caminhoVidaJogador);
            vidaDiscreta[i].addMouseListener(listeners[i]);   // reativa clique
        }

        vidaAtual = Math.min(vidaTotal, vidaAtual + valor);
        vidaSelecionado = false;
        vidaSelecionada = 0;
    }

    public void setVidaSelecionada(int vidaSelecionada) {
        this.vidaSelecionada = Math.min(vidaSelecionada, vidaAtual - 1);
    }

    public int getVidaSelecionada() {
        return vidaSelecionada;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
