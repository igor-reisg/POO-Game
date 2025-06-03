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
    private final double escalaFinal = 1.2;
    private final double escalaPasso = 0.05;

    private double escalaAtual;
    private int frameAtual;

    public CartasPanel(Carta carta) {
        this.carta = carta;
        this.setLayout(new BorderLayout());

        labelCarta = new JLabel();
        this.add(labelCarta, BorderLayout.CENTER);
        carregarImagem(escalaInicial);

        inicializaTimers();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                int largura = (int) (img.getWidth(null) * escala);
                int altura = (int) (img.getHeight(null) * escala);
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                labelCarta.setIcon(new ImageIcon(imagemRedimensionada));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }

    private void inicializaTimers() {
        int delay = 30;
        aumenta = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (escalaAtual < escalaFinal) {
                    escalaAtual += escalaPasso;
                    carregarImagem(escalaAtual);
                    frameAtual++;
                } else {
                    aumenta.stop();
                    carta.virarCarta();
                    carregarImagem(escalaAtual);
                    frameAtual = 0;
                    diminui.start();
                }
            }
        });

        diminui = new Timer(delay, e -> {
            if (escalaAtual > escalaInicial) {
                escalaAtual -= escalaPasso;
                carregarImagem(escalaAtual);
                frameAtual++;
            } else {
                diminui.stop();
            }
        });
    }

    private void animacaoVirarCarta() {
        escalaAtual = escalaInicial;
        frameAtual = 0;
        aumenta.start();
    }
}