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
    private JPanel telaAtual;

    public JanelaGUI() {
        super("Fold Or Fight");
        configurarJanela();
        configurarCursor();
        iniciarMenuPrincipal();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
    }

    private void configurarCursor() {
        cursorNormal = carregarCursor("/assets/images/cursor/cursor0.png", new Point(1, 1), "Cursor Normal");
        cursorClique = carregarCursor("/assets/images/cursor/cursor1.png", new Point(1, 1), "Cursor Clique");
        setCursor(cursorNormal);

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) event;
                if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                    setCursor(cursorClique);
                } else if (me.getID() == MouseEvent.MOUSE_RELEASED) {
                    setCursor(cursorNormal);
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    private void iniciarMenuPrincipal() {
        trocarTela(new MenuGUI(this));
    }

    private Cursor carregarCursor(String caminhoImagem, Point hotspot, String nomeCursor) {
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return Toolkit.getDefaultToolkit().createCustomCursor(img, hotspot, nomeCursor);
        }
        return Cursor.getDefaultCursor();
    }

    public void trocarTela(JPanel novaTela) {
        if (telaAtual != null) {
            remove(telaAtual);
        }
        telaAtual = novaTela;
        add(telaAtual, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}