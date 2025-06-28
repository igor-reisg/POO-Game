package gui.Jogo;

import modelos.Jogo.Round;

import javax.swing.*;
import java.net.*;
import java.awt.*;

public class RoundCounterGUI extends JPanel {
    private final ImageIcon[] roundIcons;
    private final JLabel iconLabel;
    private int currentState = 0; // 0-3 (0=inimigo1, 1=inimigo2, 2=boss, 3=completo)

    public RoundCounterGUI() {
        setOpaque(false);
        setLayout(new BorderLayout());

        // Carrega os 4 estados do round counter
        roundIcons = new ImageIcon[4];
        for (int i = 0; i < 4; i++) {
            URL url = getClass().getResource("/assets/images/JogoHUB/roundcounter_" + (i+1) + ".png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                // Redimensiona se necessário
                Image img = icon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
                roundIcons[i] = new ImageIcon(img);
            }
        }

        iconLabel = new JLabel(roundIcons[0]);
        add(iconLabel, BorderLayout.CENTER);
    }

    public void updateRoundState(int state) {
        // 0 = primeiro inimigo, 1 = segundo inimigo, 2 = boss, 3 = fase completa
        if (state >= 0 && state < roundIcons.length && roundIcons[state] != null) {
            iconLabel.setIcon(roundIcons[state]);
        }
    }

    public void reset() {
        updateRoundState(0);
    }
}
