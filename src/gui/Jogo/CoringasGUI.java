package gui.Jogo;

import modelos.Cartas.Coringa;

import modelos.Cartas.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class CoringasGUI extends JPanel {
    private final Coringa coringa;
    private final JLabel labelCoringa;

    public CoringasGUI(Coringa coringa){
        this.coringa = coringa;

        this.setLayout(new BorderLayout());
        labelCoringa = new JLabel();
        this.add(labelCoringa, BorderLayout.CENTER);
        carregarImagem();
    }

    private void carregarImagem() {
        try {
            String caminhoImagem = coringa.getImagemCaminho();
            URL urlImagem = getClass().getResource(caminhoImagem);

            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                int largura = img.getWidth(null);
                int altura = img.getHeight(null);
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                labelCoringa.setIcon(new ImageIcon(imagemRedimensionada));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }
}
