package gui.Jogo;

import modelos.Jogo.Round;

import javax.swing.*;
import java.net.*;
import java.awt.*;

public class RoundCounterGUI extends JPanel {
    private Round round;
    private final String caminhoRaiz = "/assets/images/JogoHUB/roundcounter";
    private final ImageIcon[] icon = new ImageIcon[3];
    private final JLabel panelRound = new JLabel();

    public RoundCounterGUI(Round round) {
        this.round = round;

        for (int i = 0; i < icon.length; i++) {
            URL urlImagem = getClass().getResource(caminhoRaiz + "_" + (i + 1) + ".png");

            if (urlImagem != null) {
                ImageIcon tempIcon = new ImageIcon(urlImagem);
                Image img = tempIcon.getImage();

                int largura =(int) (img.getWidth(null) * 3);
                int altura = (int) (img.getHeight(null) * 3);
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

    }

    public void changeRound() {
        // Alterar quando tiver a lógica do jogo
        round.passaRound();
        if (round.getRound() - 1 < icon.length && icon[round.getRound() - 1] != null) {
            panelRound.setIcon(icon[round.getRound() - 1]);
        } else {
            System.out.println("Round inválido ou imagem ausente: " + (round.getRound() - 1));
        }
    }

    public void resetRound() {
        if (icon[0] != null) {
            panelRound.setIcon(icon[0]);
        }
    }


}
