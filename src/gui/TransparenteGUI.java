package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TransparenteGUI extends JPanel {
    private JPanel conteudo;

    public TransparenteGUI(JPanel conteudo) {
        this.conteudo = conteudo;

        setOpaque(false);
        addMouseListener(new MouseAdapter() {});

        add(conteudo);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (conteudo != null) {
            int w = conteudo.getPreferredSize().width;
            int h = conteudo.getPreferredSize().height;
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            conteudo.setBounds(x, y, w, h);
        }
    }

}
