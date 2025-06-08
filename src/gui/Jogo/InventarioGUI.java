package gui.Jogo;

import gui.BotoesGUI;
import gui.Jogo.CoringasGUI;
import gui.StaticBackgroundPanel;
import gui.TransparenteGUI;
import modelos.Cartas.Coringa;
import modelos.Jogo.Inventario;
import modelos.Loja.MesaLoja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import java.net.URL;

public class InventarioGUI extends JPanel {
    int largura = 1550;
    int altura = 950;
    Inventario inventario;
    CoringasGUI[] coringas;
    String caminhoBackground = "/assets/images/background/pattern4.png";
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    BotoesGUI fechar, ajuda;
    JLabel totalMoedas;
    private Font fonte;
    private Dimension tamanhoTela;
    private StaticBackgroundPanel panelInventario;
    private JLabel quantidadeCoringas;

    public InventarioGUI(Inventario inventario) {
        this.inventario = inventario;

        tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        int larguraTela = tamanhoTela.width;
        int alturaTela = tamanhoTela.height;

        setLayout(null);
        setBounds((larguraTela - largura) / 2, (alturaTela - altura) / 2, largura, altura);

        //Background do inventario
        panelInventario = new StaticBackgroundPanel(caminhoBackground, largura, altura);
        panelInventario.setLayout(null);
        panelInventario.setBounds(0, 0, largura, altura);

        //Puxando a fonte para a escrita
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        //Quantidade de moedas no inventário
        totalMoedas = new JLabel("Moedas: $" + inventario.getMoedasInventarioString());
        totalMoedas.setFont(fonte);
        totalMoedas.setForeground(new Color(200, 200, 200));
        totalMoedas.setBounds(largura - 1530, altura - 70, 189, 95);
        panelInventario.add(totalMoedas);

        //Quantidade máxima de Coringas e Quantidade de Coringas atuais
        quantidadeCoringas = new JLabel("(" + inventario.getQtdCoringas() + "/" + inventario.getMaxCoringas() + ")");
        quantidadeCoringas.setFont(fonte);
        quantidadeCoringas.setForeground(new Color(200, 200, 200));
        quantidadeCoringas.setBounds(largura - 1530, altura - 100, 189, 95);
        panelInventario.add(quantidadeCoringas);

        //Botão voltar do inventário
        fechar = new BotoesGUI("inventario/", 50, 50, 0);
        fechar.getBotao().addActionListener(e -> setVisible(false));
        fechar.getBotao().setBounds(largura - 70, altura - 70, 50, 50);
        fechar.getBotao().setFocusPainted(false);
        panelInventario.add(fechar.getBotao());

        //Botão de ajuda do inventário
        ajuda = new BotoesGUI("inventario/", 50, 50, 1);
        ajuda.getBotao().addActionListener(e -> setVisible(false));
        ajuda.getBotao().setBounds(largura - 70, altura - 930, 50, 50);
        ajuda.getBotao().setFocusPainted(false);
        panelInventario.add(ajuda.getBotao());

        //Gerar coringas que tem no inventario
        coringas = new CoringasGUI[inventario.listarCoringas().size()];
        for (int i = 0; i < inventario.listarCoringas().size(); i++) {
            Coringa coringa = inventario.listarCoringas().get(i);
            coringas[i] = new CoringasGUI(coringa, 100 + i * 200, 100, 120, 180);
        }

        add(panelInventario);
        setVisible(false);
    }

    public void mostrarCoringas() {
        for (CoringasGUI c : coringas) {
            if (c.getParent() == null) {
                panelInventario.add(c);
            }
        }
        panelInventario.revalidate();
        panelInventario.repaint();
    }
}
