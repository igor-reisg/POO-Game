package gui.Jogo;

import modelos.Cartas.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class CartasPanel extends JPanel {
    private final Carta carta;
    private final JLabel labelCarta;

    private Timer aumenta;
    private Timer diminui;

    private final double escalaInicial = 1.0;
    private int largura;
    private int altura;

    public CartasPanel(Carta carta) {
        this.carta = carta;
        this.setLayout(null);
        this.setOpaque(false);

        labelCarta = new JLabel();
        labelCarta.setOpaque(false);
        this.add(labelCarta);
        carregarImagem(escalaInicial);

        inicializaTimers();

        this.carta.setCartaListener(new Carta.CartaListener() {
            @Override
            public void aoVirarCarta() {
                animacaoVirarCarta();
            }
        });
    }


    private void carregarImagem(double escala) {
        try {
            String caminhoImagem = carta.getImagemCaminho();
            URL urlImagem = getClass().getResource(caminhoImagem);

            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                this.largura = (int) (img.getWidth(null) * escala);
                this.altura = (int) (img.getHeight(null) * escala);
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

                labelCarta.setIcon(new ImageIcon(imagemRedimensionada));
                labelCarta.setBounds((getWidth() - largura) / 2, (getHeight() - altura) / 2, largura, altura); // <- centraliza
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }


    public int getWidth(){
        return largura;
    }

    public int getHeight(){
        return altura;
    }

    private void inicializaTimers() {
        int delay = 20;
        int passo = 20; // pixels por frame
        final int larguraOriginal = carta.getLarguraOriginal();
        final int alturaOriginal = carta.getAlturaOriginal();

        aumenta = new Timer(delay, null);
        diminui = new Timer(delay, null);

        aumenta.addActionListener(new ActionListener() {
            int larguraAtual = larguraOriginal;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (larguraAtual > 0) {
                    larguraAtual -= passo;
                    if (larguraAtual < 0) larguraAtual = 0;
                    atualizarImagemComLargura(larguraAtual, alturaOriginal);
                } else {
                    aumenta.stop();
                    carregarImagem(1.0);
                    diminui.start();
                }
            }
        });

        diminui.addActionListener(new ActionListener() {
            int larguraAtual = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (larguraAtual < larguraOriginal) {
                    larguraAtual += passo;
                    if (larguraAtual > larguraOriginal) larguraAtual = larguraOriginal;
                    atualizarImagemComLargura(larguraAtual, alturaOriginal);
                } else {
                    diminui.stop();
                }
            }
        });
    }

    private void atualizarImagemComLargura(int largura, int altura) {
        try {
            String caminhoImagem = carta.getImagemCaminho();
            URL urlImagem = getClass().getResource(caminhoImagem);

            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                labelCarta.setIcon(new ImageIcon(imagemRedimensionada));
                labelCarta.setBounds((getWidth() - largura) / 2, (getHeight() - altura) / 2, largura, altura); // <- centraliza
            }
        } catch (Exception e) {
            System.out.println("Erro ao redimensionar carta: " + e.getMessage());
        }
    }


    public void animacaoVirarCarta() {
        aumenta.start();
    }




}

