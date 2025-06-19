package gui.Jogo;

import gui.StaticBackgroundPanel;
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
        JLabel infos = new JLabel("<html><b>Nome: </b>" + coringa.getNome() +
                                       "<br><b>Descrição: </b>" + coringa.getDescricao() +
                                       "<br><b>Raridade: </b>" + coringa.getRaridade() +
                                       "<br><b>Preço: </b>" + coringa.getPreco() +
                                       "<br><b>XP: </b>" + coringa.getXp() +
                                       "<br><b>Level: </b>" + coringa.getLevel() + "</html>");

        infos.setForeground(new Color(40, 40, 40));
        infos.setSize(infos.getPreferredSize());
        infos.setFont(fonte);
        infos.setBounds(10, 10, (largura * 2) - 20, altura - 20);
        previewPanel.add(infos, JLayeredPane.PALETTE_LAYER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Window window = SwingUtilities.getWindowAncestor(CoringasGUI.this);
                if (window instanceof JFrame) {
                    JLayeredPane layered = ((JFrame) window).getLayeredPane();

                    if (previewPanel.getParent() != layered) {
                        layered.add(previewPanel, JLayeredPane.PALETTE_LAYER);
                    }

                    previewPanel.setLocation(getX() + largura + 10, getY());
                    previewPanel.setVisible(true);
                    layered.repaint();
                } else {
                    System.out.println("Window ancestor is not JFrame or not found.");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                previewPanel.setVisible(false);
            }
        });
    }

    public void moveTo(Point destino, int passos, Runnable noFinal) {
        Point origem = getLocation();
        int dx = (destino.x - origem.x) / passos;
        int dy = (destino.y - origem.y) / passos;

        Timer timer = new Timer(10, null);

        timer.addActionListener(new ActionListener() {
            int contador = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador < passos) {
                    setLocation(getX() + dx, getY() + dy);
                    contador++;
                } else {
                    setLocation(destino);
                    timer.stop();
                    if (noFinal != null) noFinal.run();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgCoringa2 != null) {
            imgCoringa2.paintIcon(this, g, 0, 0);
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
}
