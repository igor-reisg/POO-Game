package gui;

import gui.Menu.MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.net.URL;

public class JanelaGUI extends JFrame {
    private Cursor cursorClique;
    private Cursor cursorNormal;

    public JanelaGUI() {
        super("Fold Or Fight");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setUndecorated(true);

        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tamanhoTela.width, tamanhoTela.height);

        //Image icon = Toolkit.getDefaultToolkit().getImage("/assets/images/logo/logo01.png");
        //setIconImage(icon);

        cursorNormal = carregarCursor("/assets/images/cursor/cursor0.png", new Point(1, 1), "Cursor Normal");
        cursorClique = carregarCursor("/assets/images/cursor/cursor1.png", new Point(1, 1), "Cursor Clique");

        setCursor(cursorNormal);

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) event;
                    if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                        setCursor(cursorClique);
                    } else if (me.getID() == MouseEvent.MOUSE_RELEASED) {
                        setCursor(cursorNormal);
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        setContentPane(new MenuGUI(this));
    }

    private Cursor carregarCursor(String caminhoImagem, Point hotspot, String nomeCursor) {
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return Toolkit.getDefaultToolkit().createCustomCursor(img, hotspot, nomeCursor);
        } else {
            System.out.println(caminhoImagem);
            return Cursor.getDefaultCursor();
        }
    }

    public void trocarTela(JPanel novaTela) {
        getContentPane().removeAll();
        setContentPane(novaTela);
        revalidate();
        repaint();
    }
}
