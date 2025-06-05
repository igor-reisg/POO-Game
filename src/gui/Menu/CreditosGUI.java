package gui.Menu;

import gui.BotoesGUI;
import gui.JanelaGUI;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class CreditosGUI extends JPanel implements Runnable {
    JanelaGUI app;
    private Font fonte;
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    private final String caminhoLogo = "/assets/images/logo/logo01.png";
    private final String[] creditos = {
            "Título do Jogo: Fold or Fight",
            "Versão do Jogo: 1.0",
            "Data de Lançamento: 10/10/2010",
            " ",
            "Idealização: Caio Cesar, Igor Reis, Fernando Hiroshi",
            "Desenvolvimento: Caio Cesar, Igor Reis, Fernando Hiroshi",
            "Design de Jogo: Caio Cesar, Igor Reis, Fernando Hiroshi",
            "Design de Interface: Caio Cesar, Igor Reis",
            "Arte Visual: Fernando Hiroshi",
            "Efeitos Sonoros: ",
            "Trilha Sonora: ",
            "Ferramentas e Tecnologias Utilizadas: Java Swing, Java",
    };
    BotoesGUI botaoVoltar = new BotoesGUI("menu/", 66, 66, 5);
    private int y = 600;
    private final Image logo;
    private int alturaLogo;

    public CreditosGUI(JanelaGUI app) {
        this.app = app;

        logo = new ImageIcon(Objects.requireNonNull(getClass().getResource(caminhoLogo))).getImage();
        if (logo != null) {
            alturaLogo = logo.getHeight(null) + 40;
        }

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelSuperior.add(botaoVoltar.getBotao());
        botaoVoltar.getBotao().addActionListener(e -> app.trocarTela(new MenuGUI(app)));
        panelSuperior.setOpaque(false);
        add(panelSuperior, BorderLayout.NORTH);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(fonte);

        if (logo != null) {
            int larguraLogo = logo.getWidth(this);
            int xLogo = (getWidth() - larguraLogo) / 2;
            int yLogo = 20;
            g.drawImage(logo, xLogo, yLogo, this);
        }

        FontMetrics fm = g.getFontMetrics();
        int espacamento = 60;

        for (int i = 0; i < creditos.length; i++) {
            int yLinha = y + i * espacamento;

            if (yLinha > alturaLogo) {
                String linha = creditos[i];
                int larguraTexto = fm.stringWidth(linha);
                int x = (getWidth() - larguraTexto) / 2;
                g.drawString(linha, x, yLinha);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            int velocidade = 1;
            y -= velocidade;
            repaint();

            if (y + creditos.length * 60 < alturaLogo) {
                app.trocarTela(new MenuGUI(app));
                return;
            }

            try {
                Thread.sleep(35);
            } catch (Exception e) {
                System.out.println("Erro ao movimentar créditos: " + e);
            }
        }
    }
}
