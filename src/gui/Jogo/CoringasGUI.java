package gui.Jogo;

import gui.StaticBackgroundPanel;
import modelos.Cartas.Coringa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class CoringasGUI extends JPanel {
    private final Coringa coringa;
    private ImageIcon imgCoringa2;
    private StaticBackgroundPanel previewPanel;
    private final int largura;
    private final int altura;

    public CoringasGUI(Coringa coringa, int posX, int posY, int largura, int altura) {
        this.coringa = coringa;
        this.largura = largura;
        this.altura = altura;

        carregarImagem();

        setSize(largura, altura);
        setLocation(posX, posY);
        setOpaque(false);
        setLayout(null);

        previewPanel = new StaticBackgroundPanel(coringa.getImagemCaminho(), largura, altura);
        previewPanel.setBounds(posX + largura + 10, posY, largura, altura);
        previewPanel.setVisible(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Container parent = SwingUtilities.getAncestorOfClass(JLayeredPane.class, CoringasGUI.this);
                if (parent instanceof JLayeredPane) {
                    JLayeredPane layered = (JLayeredPane) parent;

                    if (previewPanel.getParent() != layered) {
                        layered.add(previewPanel, JLayeredPane.PALETTE_LAYER);
                    }

                    previewPanel.setLocation(getX() + largura + 10, getY());
                    previewPanel.setVisible(true);
                    layered.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                previewPanel.setVisible(false);
            }
        });
    }

    public void moveTo(Point destino, int passos, Runnable noFinal) {
        Point origem = getLocation();
        int dx = (destino.x - origem.x) / passos;
        int dy = (destino.y - origem.y) / passos;

        Timer timer = new Timer(10, null);

        timer.addActionListener(new ActionListener() {
            int contador = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador < passos) {
                    setLocation(getX() + dx, getY() + dy);
                    contador++;
                } else {
                    setLocation(destino);
                    timer.stop();
                    if (noFinal != null) noFinal.run();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgCoringa2 != null) {
            imgCoringa2.paintIcon(this, g, 0, 0);
        }
    }

    private void carregarImagem() {
        URL url = getClass().getResource(coringa.getImagemCaminho());
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            imgCoringa2 = new ImageIcon(img);
        }
    }
}
