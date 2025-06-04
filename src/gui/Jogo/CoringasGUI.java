package gui.Jogo;

import modelos.Cartas.Coringa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class CoringasGUI extends JPanel {
    private final Coringa coringa;
    private ImageIcon imgCoringa;
    private Point prevMouse;
    private ImageIcon imgCoringa2;

    public CoringasGUI(Coringa coringa, int posX, int posY, int largura, int altura) {
        this.coringa = coringa;

        // Carregar imagem
        URL url = getClass().getResource(coringa.getImagemCaminho());
        if (url != null) {
            imgCoringa = new ImageIcon(url);
            Image imgRed = imgCoringa.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            imgCoringa2 = new ImageIcon(imgRed);
        }

        setSize(largura, altura);
        setOpaque(false);
        setLocation(posX, posY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevMouse = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - prevMouse.x;
                int dy = e.getY() - prevMouse.y;
                Point location = getLocation();
                setLocation(location.x + dx, location.y + dy);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgCoringa2 != null) {
            imgCoringa2.paintIcon(this, g, 0, 0);
        }
    }
}
