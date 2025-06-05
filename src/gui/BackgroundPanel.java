package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.Objects;

public class BackgroundPanel extends JPanel {
    private int x = 0;
    private BufferedImage img;
    private int y = 0;
    private int velocidade = 1;

    public BackgroundPanel(String caminhoImagem) {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(caminhoImagem)));
        } catch (Exception ignored) {};

        setLayout(new BorderLayout());
        setDoubleBuffered(true);

        new Timer(32, e -> {
            x -= velocidade;
            y -= velocidade;

            if (img != null && x < -img.getWidth() && y < -img.getHeight()) {
                x = 0;
                y = 0;
            }
            repaint();
        }).start();

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
