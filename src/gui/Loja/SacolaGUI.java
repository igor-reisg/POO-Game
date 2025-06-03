package gui.Loja;

import gui.BotoesGUI;
import gui.JanelaGUI;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class SacolaGUI extends JPanel {
    JLayeredPane painelCartas;
    int cartasNaSacola = 0;
    double total = 0.0;
    JLabel labelTotal;
    private Font fonte;
    private final String caminhoFonte = "/assets/fonts/Retro Gaming.ttf";

    public SacolaGUI() {
        setLayout(new BorderLayout());

        painelCartas = new JLayeredPane();
        painelCartas.setPreferredSize(new Dimension(120, 160));
        painelCartas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel painelCentro = new JPanel(null);
        painelCentro.setPreferredSize(new Dimension(120, 160));
        painelCentro.add(painelCartas);

        labelTotal = new JLabel("Total: $0.00");
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(32f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 32);
            System.out.println("Erro ao carregar fonte: " + e);
        }
        labelTotal.setFont(fonte);

        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotal.add(labelTotal);

        add(painelCentro, BorderLayout.CENTER);
        add(painelTotal, BorderLayout.SOUTH);
    }

    public void adicionarCarta() {

    }
}
