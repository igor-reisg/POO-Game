package gui;

import modelos.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class BackgroundPanel extends JPanel implements Runnable {
    private int x = 0;
    private Image img;
    private int y = 0;
    private int velocidade = 1;

    public BackgroundPanel(String caminhoImagem) {
        URL urlImagem = getClass().getResource(caminhoImagem);

        if (urlImagem != null) {
            ImageIcon imagem = new ImageIcon(urlImagem);
            img = imagem.getImage();
        }

        setLayout(new BorderLayout());
        setIgnoreRepaint(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                x -= velocidade;
                y -= velocidade;

                if (img != null && x < -img.getWidth(null) && y < -img.getHeight(null)) {
                    x = 0;
                    y = 0;
                }
                repaint();
                Thread.sleep(32);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem de fundo: " + e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img != null) {
            int largura = img.getWidth(this);
            int altura = img.getHeight(this);

            for (int i = x; i < getWidth(); i += largura) {
                for (int j = y; j < getHeight(); j += altura) {
                    g.drawImage(img, i, j, this);
                }
            }

            for (int i = x - largura; i > -largura; i -= largura) {
                for (int j = y; j < getHeight(); j += altura) {
                    g.drawImage(img, i, j, this);
                }
            }

            for (int i = x; i < getWidth(); i += largura) {
                for (int j = y - altura; j > -altura; j -= altura) {
                    g.drawImage(img, i, j, this);
                }
            }
        }
    }

}
