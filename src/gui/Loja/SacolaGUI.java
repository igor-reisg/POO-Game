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
    Sacola sacola;
    private JLabel labelSacola; //TA DANDO BO PQ É JLABEL, TENTEI TROCAR MAS NAO FUNFA (O CERTO É SER JPANEL OU O BACKGROUNDPANEL)
    int largura = 1613;
    int altura = 936;
    private JLabel textPrice;

    public SacolaGUI(Sacola sacola) {
        this.sacola = sacola;
        labelSacola = new JLabel();
        setMinimumSize(new Dimension(largura, altura));
        labelSacola.setLayout(null);
        carregarImagem();
        labelSacola.setOpaque(false);

        //Puxando a fonte para a escrita
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        textPrice = new JLabel("Valor: " + sacola.getPrecoTotal());
        textPrice.setFont(fonte);
        textPrice.setForeground(new Color(40, 40, 40));
        textPrice.setBounds(100, 50, 300, 50);
        labelSacola.add(textPrice);

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
