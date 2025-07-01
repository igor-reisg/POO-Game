package gui.Loja;

import modelos.Cartas.Coringa;
import modelos.Loja.Sacola;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class SacolaGUI extends JPanel {
    private Font fonte;
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    private final String caminhoMoeda = "/assets/images/inventario/moeda.png";
    Sacola sacola;
    private JLabel labelSacola;
    int largura = 1613;
    int altura = 936;
    private JLabel textPrice;
    private JLabel labelMoeda;

    public SacolaGUI(Sacola sacola) {
        this.sacola = sacola;
        // 1. Mude o layout do painel principal. BorderLayout é uma boa escolha.
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(largura, altura));
        setOpaque(false);

        // Carregando a fonte
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        labelSacola = new JLabel();
        labelSacola.setLayout(null);
        labelSacola.setOpaque(false);
        carregarImagem();
        this.add(labelSacola, BorderLayout.CENTER);

        // TextHolder
        JLabel textholder = new JLabel();
        String caminhoTextholder = "/assets/images/mesaPrincipal/textholder.png";
        URL urlHolder = getClass().getResource(caminhoTextholder);
        if (urlHolder != null) {
            ImageIcon original = new ImageIcon(urlHolder);
            Image img = original.getImage().getScaledInstance(200, 70, Image.SCALE_SMOOTH);
            textholder.setIcon(new ImageIcon(img));
        }
        textholder.setBounds((largura - 330), (altura - 340), 200, 70);

        labelMoeda = new JLabel();
        URL url = getClass().getResource(caminhoMoeda);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            labelMoeda.setIcon(new ImageIcon(img));
        }
        labelMoeda.setBounds(textholder.getX() + 50, textholder.getY() + 20, 30, 30);

        // Texto sobre o textholder
        textPrice = new JLabel(String.valueOf(sacola.getPrecoTotal()));
        textPrice.setFont(fonte);
        textPrice.setForeground(new Color(40, 40, 40));
        textPrice.setBounds(textholder.getX() + 90, textholder.getY() + 20, 170, 30);

        labelSacola.add(labelMoeda);
        labelSacola.add(textPrice);
        labelSacola.add(textholder);
    }

    public void atualizarPreco() {
        textPrice.setText(String.valueOf(sacola.getPrecoTotal()));
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
