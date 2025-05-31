package gui;

import javax.swing.*;
import java.awt.*;
import java.net.*;

public class StaticBackgroundPanel extends JPanel {
    Image img;

    public StaticBackgroundPanel(String caminhoImagem, int x, int y) {
        setPreferredSize(new Dimension(x, y));

        URL urlImagem = getClass().getResource(caminhoImagem);
        if (urlImagem != null) {
            ImageIcon imagem = new ImageIcon(urlImagem);
            img = imagem.getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img != null) {
            int w = img.getWidth(this);
            int h = img.getHeight(this);

            for (int x = 0; x < getWidth(); x += w) {
                for (int y = 0; y < getHeight(); y += h) {
                    g.drawImage(img, x, y, this);
                }
            }
        }
    }
}
