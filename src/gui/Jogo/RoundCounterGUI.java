package gui.Jogo;

import javax.swing.*;
import java.net.*;
import java.awt.*;

public class RoundCounterGUI extends JPanel {
    private int round;
    private final String caminhoRaiz = "/assets/images/botoes/jogo/roundcounter";
    private final ImageIcon[] icon = new ImageIcon[3];
    private final JLabel panelRound = new JLabel();

    public RoundCounterGUI(int round) {
        this.round = round;

        for (int i = 0; i < icon.length; i++) {
            URL urlImagem = getClass().getResource(caminhoRaiz + "_" + (i + 1) + ".png");

            if (urlImagem != null) {
                ImageIcon tempIcon = new ImageIcon(urlImagem);
                Image img = tempIcon.getImage();

                int largura = img.getWidth(null) * 6;
                int altura = img.getHeight(null) * 6;
                Image imgEscalada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

                icon[i] = new ImageIcon(imgEscalada);
            } else {
                System.out.println("Imagem não encontrada: " + caminhoRaiz + "_" + (i + 1) + ".png");
                icon[i] = null;
            }
        }

        // Define o primeiro ícone, se existir
        if (icon[0] != null) {
            panelRound.setIcon(icon[0]);
        }

        setOpaque(false);
        setLayout(new BorderLayout());
        add(panelRound, BorderLayout.CENTER);

        System.out.println("RoundCounterGUI inicializado.");
    }

    public void changeRound() {
        if (round >= 0 && round < icon.length && icon[round] != null) {
            panelRound.setIcon(icon[round]);
            System.out.println("Round alterado para: " + round);
        } else {
            System.out.println("Round inválido ou imagem ausente: " + round);
        }
    }

    public void atualizarRound(int novoRound) {
        this.round = novoRound;
        changeRound();
    }
}
