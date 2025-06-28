package gui.Jogo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class IconeGUI extends JPanel {

    private static final int MARGIN_LEFT = 0;   // Ajuste horizontal
    private static final int MARGIN_TOP = 10;    // Ajuste vertical
    private static final int NAME_OFFSET = 0;
    private final JLabel frameLabel;    // Frame exterior (container)
    private final JLabel avatarLabel;   // Ícone interior
    private final JLabel nameLabel;     // Nome do jogador
    private final String[] bossStates;  // Estados do boss
    private final boolean isBoss;       // Flag para boss

    // Margens e padding do design original
    private static final int PADDING_TOP = 10;
    private static final int PADDING_LEFT = 10;
    private static final int AVATAR_WIDTH = 170;
    private static final int AVATAR_HEIGHT = 120;
    private static final int NAME_HEIGHT = 20;

    public IconeGUI(String avatarPath, String nome, boolean isBoss, String[] bossStates) {
        this.isBoss = isBoss;
        this.bossStates = bossStates;

        setOpaque(false);
        setLayout(new BorderLayout()); // Mudamos para BorderLayout mais flexível

        // Frame principal com margens
        frameLabel = new JLabel();
        frameLabel.setBorder(BorderFactory.createEmptyBorder(MARGIN_TOP, MARGIN_LEFT, 0, 0));
        carregarImagem("/assets/images/frames/framePrincipal/frame01.png", frameLabel);
        frameLabel.setLayout(new BorderLayout());

        // Configuração do nome com offset
        nameLabel = new JLabel(nome, SwingConstants.CENTER);
        nameLabel.setFont(loadCustomFont(18f));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, NAME_OFFSET, 0)); // Offset vertical
        frameLabel.add(nameLabel, BorderLayout.NORTH);

        // Avatar com posicionamento preciso
        avatarLabel = new JLabel();
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(-2, 10, 0, 0)); // Ajuste fino aqui
        carregarImagem(avatarPath, avatarLabel);
        frameLabel.add(avatarLabel, BorderLayout.CENTER);

        add(frameLabel);
    }

    private Font loadCustomFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/assets/fonts/RetroGaming.ttf")).deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.PLAIN, (int)size);
        }
    }

    private void carregarImagem(String path, JLabel target) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                target.setIcon(new ImageIcon(url));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + path);
        }
    }

    public void atualizarEstadoBoss(int estado) {
        if (isBoss && bossStates != null && estado >= 0 && estado < bossStates.length) {
            carregarImagem(bossStates[estado], avatarLabel);
        }
    }

    public void setNome(String nome) {
        nameLabel.setText(nome);
    }

    public void setImagem(String caminhoImagem) {
        carregarImagem(caminhoImagem, avatarLabel);
    }
}