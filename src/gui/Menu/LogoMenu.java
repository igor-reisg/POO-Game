package gui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Objects;
import javax.imageio.*;

public class LogoMenu extends JPanel {
    private final String caminhoImagens = "/assets/images/logo/logogif/logogif_";
    private final BufferedImage[] imagens = new BufferedImage[6];
    private int frameAtual = 0;

    public LogoMenu() {
        try {
            for (int i = 0; i < 6; i++) {
                imagens[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource(caminhoImagens + i + ".png")));
            }
        } catch (Exception ignored) {}

        Timer timer = new Timer(150, e -> {
            frameAtual = (frameAtual + 1) % imagens.length;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagens[frameAtual] != null) {
            int largura= 1055;
            int altura = 740;

            int x = (getWidth() - largura) / 2 - 25;
            int y = (getHeight() - altura) / 2 - 80;

            g.drawImage(imagens[frameAtual], x, y, largura, altura, this);
        }
    }
}
