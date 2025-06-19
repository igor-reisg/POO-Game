package gui.Jogo;

import modelos.Jogo.Pote;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PoteGUI extends JPanel {
    private int largura;
    private int altura;
    private final Pote pote;
    private final JLabel panelPote = new JLabel();
    private final String caminhoRaiz = "/assets/images/JogoHUB";
    private final JLabel[] unidadesSangue = new JLabel[30];

    public PoteGUI(Pote pote) {
        this.pote = pote;
        setOpaque(false);
        setDimensions();
        panelPote.setLayout(null);
        panelPote.setOpaque(false);

        // Carrega e adiciona a imagem de fundo do pote
        carregarImagem(panelPote, caminhoRaiz + "/barrapote.png");
        add(panelPote);

        // Cria e adiciona os 30 slots de sangue (vazios no início)
        for (int i = 29; i >= 0; i--) {
            unidadesSangue[i] = new JLabel();
            unidadesSangue[i].setBounds(
                    3,
                      altura - 16 - (i+1) * 14,
                    75,
                    26);
            carregarImagem(unidadesSangue[i], null); // Começa vazio
            panelPote.add(unidadesSangue[i]);
        }
    }

    private void setDimensions() {
        try {
            URL urlImagem = getClass().getResource("/assets/images/JogoHUB/barrapote.png");
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                this.largura = img.getWidth(null);
                this.altura = img.getHeight(null);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da vida: " + e.getMessage());
        }
    }

    private void carregarImagem(JLabel label, String caminhoImagem) {
        try {
            if (caminhoImagem != null) {
                URL urlImagem = getClass().getResource(caminhoImagem);
                if (urlImagem != null) {
                    label.setIcon(new ImageIcon(urlImagem));
                } else {
                    label.setIcon(null);
                }
            } else {
                label.setIcon(null);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem do pote: " + e.getMessage());
        }
    }
    public void adicionarPote(int quantidade) {
        // Garante que a quantidade está no intervalo válido
        quantidade = Math.max(0, Math.min(pote.getQuantidade(), unidadesSangue.length));

        for (int i = 0; i < unidadesSangue.length; i++) {
            if (i < quantidade) {
                carregarImagem(unidadesSangue[i], caminhoRaiz + "/unidadepote.png");
            } else {
                carregarImagem(unidadesSangue[i], null);
            }
        }
    }
    public Pote getPote(){
        return pote;
    }

}
