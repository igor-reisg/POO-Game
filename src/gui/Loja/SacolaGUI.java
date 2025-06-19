package gui.Loja;

import modelos.Cartas.Coringa;
import modelos.Loja.Sacola;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SacolaGUI extends JPanel {
    private Font fonte;
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    Sacola sacola;
    private JLabel labelSacola;
    int largura = 1613;
    int altura = 936;

    public SacolaGUI(Sacola sacola) {
        this.sacola = sacola;
        labelSacola = new JLabel();
        setMinimumSize(new Dimension(largura, altura));
        labelSacola.setLayout(null);
        carregarImagem();

        add(labelSacola);
    }

    private void carregarImagem() {
        String caminhoImagem = "/assets/images/mesaPrincipal/carrinholoja.png";
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            labelSacola.setIcon(new ImageIcon(img));
        }
    }
}
