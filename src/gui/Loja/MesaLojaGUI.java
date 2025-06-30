package gui.Loja;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;
import modelos.Loja.MesaLoja;
import modelos.Loja.Sacola;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class MesaLojaGUI extends JPanel {
    private JLabel labelMesa;
    int largura = 1920;
    int altura = 1080;
    MesaLoja mesa;
    CoringasGUI[] coringasMesa;
    private Sacola sacola;
    private SacolaGUI sacolaGUI;
    private List<CoringasGUI> coringasSacola;

    public MesaLojaGUI(MesaLoja mesa, Sacola sacola, SacolaGUI sacolaGUI) {
        this.mesa = mesa;
        this.sacola = sacola;
        this.sacolaGUI = sacolaGUI;
        this.coringasSacola = new ArrayList<>();

        labelMesa = new JLabel();
        setMinimumSize(new Dimension(largura, altura));
        labelMesa.setLayout(null);

        carregarImagem();

        gerarCoringas();

        add(labelMesa);
    }

    public void atualizarCartas() {
        if (coringasMesa != null) {
            for (CoringasGUI coringaGUI : coringasMesa) {
                labelMesa.remove(coringaGUI);
            }
        }

        gerarCoringas();

        labelMesa.revalidate();
        labelMesa.repaint();
    }

    private void gerarCoringas() {
        Coringa[] cartas = mesa.getCartas();
        int qtd = Math.min(cartas.length, 6);
        coringasMesa = new CoringasGUI[qtd];

        int espacamentoX = 400;
        int espacamentoY = 450;
        int xInicial = 200;
        int yInicial = 175;

        for (int i = 0; i < qtd; i++) {
            Coringa carta = cartas[i];
            int lin = i / 3;
            int col = i % 3;
            int posX = xInicial + col * espacamentoX;
            int posY = yInicial + lin * espacamentoY;

            CoringasGUI panelCoringa = new CoringasGUI(carta, posX, posY, 212, 298);
            coringasMesa[i] = panelCoringa;

            panelCoringa.setLocation(posX, posY);
            int finalI = i;

            //PRESSIONA MOUSE NO CORINGA DA LOJA. VAI PARA A SACOLA E FAZ MUITAS COISAS BEM IMPORTANTES MESMO
            panelCoringa.addMouseListener(new MouseAdapter() {
                private boolean movido = false;

                @Override
                public void mousePressed(MouseEvent e) {
                    if (movido) return; // Se já foi movido não faz nada
                    JComponent coringaComponent = (JComponent) e.getSource();
                    Point destino = new Point(1550, 200);

                    sacola.adicionarCoringaSacola(carta);
                    coringasSacola.add(panelCoringa);
                    sacolaGUI.atualizarPreco();

                    moverCoringaSacola(coringaComponent, destino);

                    System.out.println("Coringa adicionado. Total: " + sacola.getQtdCoringas() + ", Preço: " + sacola.getPrecoTotal());
                    movido = true;
                }
            });

            labelMesa.add(panelCoringa, Integer.valueOf(4));

            labelMesa.add(panelCoringa, Integer.valueOf(4));
        }
    }

    public void setHoverEnabled(boolean enabled) {
        if (coringasMesa != null) {
            for (CoringasGUI coringaGUI : coringasMesa) {
                coringaGUI.setHoverEnabled(enabled);
            }
        }
    }

    private void moverCoringaSacola(JComponent comp, Point destino) {
        Point origem = comp.getLocation();
        int fps = 60;
        int frames = 500 * fps / 1000;

        Timer timer = new Timer(1000 / fps, null);
        final int[] frame = {0};

        timer.addActionListener(e -> {
            frame[0]++;
            float t = frame[0] / (float) frames;

            int x = (int) (origem.x + (destino.x - origem.x) * t);
            int y = (int) (origem.y + (destino.y - origem.y) * t);

            comp.setLocation(x, y);
            comp.getParent().repaint();

            if (frame[0] >= frames) {
                comp.setLocation(destino);
                timer.stop();
            }
        });

        timer.start();
    }

    public void removerCartasCompradas() {
        for (CoringasGUI coringaGUI : coringasSacola) {
            labelMesa.remove(coringaGUI);
        }
        coringasSacola.clear();
        labelMesa.revalidate();
        labelMesa.repaint();
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
