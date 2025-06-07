package gui.Loja;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;
import modelos.Loja.MesaLoja;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MesaLojaGUI extends JPanel {
    private JLabel labelMesa;
    int largura = 1920;
    int altura = 1080;
    MesaLoja mesa;
    CoringasGUI[] coringasMesa;

    public MesaLojaGUI(MesaLoja mesa) {
        this.mesa = mesa;
        labelMesa = new JLabel();
        setMinimumSize(new Dimension(largura, altura));
        labelMesa.setLayout(null);

        carregarImagem();

        Coringa[] cartas = mesa.getCartas();
        int qtd = Math.min(cartas.length, 6);
        coringasMesa = new CoringasGUI[qtd];

        int espacamentoX = 300;
        int espacamentoY = 260;

        int xInicial = largura / 2 - (3 * espacamentoX) / 2 + 15;
        int yInicial = 120;

        for (int i = 0; i < qtd; i++) {
            Coringa carta = cartas[i];

            int lin = i / 3;
            int col = i % 3;

            int posX = xInicial + col * espacamentoX;
            int posY = yInicial + lin * espacamentoY;

            CoringasGUI panelCoringas = new CoringasGUI(carta, posX, posY, 176, 248);

            coringasMesa[i] = panelCoringas;
            labelMesa.add(panelCoringas);
        }
        add(labelMesa);
    }

    private void carregarImagem() {
        String caminhoImagem = "/assets/images/mesaPrincipal/loja.png";
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            labelMesa.setIcon(new ImageIcon(img));
        }
    }

}
