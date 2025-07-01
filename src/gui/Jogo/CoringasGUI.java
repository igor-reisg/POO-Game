package gui.Jogo;

import modelos.Cartas.Coringa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.net.URL;

public class CoringasGUI extends JPanel {
    private final Coringa coringa;
    private ImageIcon imgCoringa2;
    private JLayeredPane previewPanel;
    private final int largura;
    private final int altura;
    private String TextHolderImagem = "/assets/images/cartas/hover/hover0.png";
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    private Font fonte;
    private boolean hoverEnabled = true;

    public CoringasGUI(Coringa coringa, int posX, int posY, int largura, int altura) {
        this.coringa = coringa;
        this.largura = largura;
        this.altura = altura;

        carregarImagem(coringa.getImagemCaminho());

        setSize(largura, altura);
        setLocation(posX, posY);
        setOpaque(false);
        setLayout(null);

        previewPanel = new JLayeredPane();
        previewPanel.setVisible(false);
        previewPanel.setBounds(posX + (largura * 2) + 100, posY, largura * 2, altura);

        //Imagem de fundo do previewPanel
        JLabel fundo = new JLabel();
        URL url = getClass().getResource(TextHolderImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(largura * 2, altura, Image.SCALE_SMOOTH);
            fundo.setIcon(new ImageIcon(img));
        }
        fundo.setBounds(0, 0, largura * 2, altura);
        previewPanel.add(fundo, JLayeredPane.DEFAULT_LAYER);

        //Carregando fonte
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        //Informações das características do coringa
        String htmlTexto = "<html>" +
                "<table style='font-family:" + fonte.getFamily() + ";font-size:24pt;color:#282828;line-height:1.2'>" +
                "<tr><td valign='top'><b>Nome:</b></td><td>" + coringa.getNome() + "</td></tr>" +
                "<tr><td valign='top'><b>Descrição:</b></td><td>" + coringa.getDescricao() + "</td></tr>" +
                "<tr><td valign='top'><b>Raridade:</b></td><td>" + coringa.getRaridade() + "</td></tr>" +
                "<tr><td valign='top'><b>Preço:</b></td><td>" + coringa.getPreco() + "</td></tr>" +
                "</table></html>";

        JLabel infos = new JLabel(htmlTexto);
        infos.setFont(fonte.deriveFont(24f));
        infos.setBounds(10, 10, (largura * 2) - 20, altura - 20);
        previewPanel.add(infos, JLayeredPane.PALETTE_LAYER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverEnabled) {
                    Window window = SwingUtilities.getWindowAncestor(CoringasGUI.this);
                    if (window instanceof JFrame) {
                        JLayeredPane layered = ((JFrame) window).getLayeredPane();

                        if (previewPanel.getParent() != layered) {
                            layered.add(previewPanel, JLayeredPane.PALETTE_LAYER);
                        }

                        previewPanel.setLocation(getX() + largura + 10, getY());
                        previewPanel.setVisible(true);
                        layered.repaint();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverEnabled) {
                    previewPanel.setVisible(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgCoringa2 != null) {
            imgCoringa2.paintIcon(this, g, 0, 0);
        }
    }

    public void setHoverEnabled(boolean enabled) {
        this.hoverEnabled = enabled;
        if (!enabled && previewPanel.isVisible()) {
            previewPanel.setVisible(false);
        }
    }

    private void carregarImagem(String caminho) {
        URL url = getClass().getResource(caminho);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            imgCoringa2 = new ImageIcon(img);
        }
    }

    public Coringa getCoringaStats() {
        return coringa;
    }
}
