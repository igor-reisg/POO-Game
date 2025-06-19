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
    private final String caminhoBackground = "/assets/images/inventario/inventario.png";
    private final String caminhoMoeda = "/assets/images/inventario/moeda.png";
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    BotoesGUI fechar, ajuda;
    JLabel totalMoedas;
    private Font fonte;
    private Dimension tamanhoTela;
    private StaticBackgroundPanel panelInventario;
    private JLabel quantidadeCoringas;
    private final JLabel labelTextHolder;
    private JLabel labelMoeda;

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
        panelInventario.setOpaque(false);

        //Puxando a fonte para a escrita
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        //Label da Moeda inventario
        labelMoeda = new JLabel();
        URL url = getClass().getResource(caminhoMoeda);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            labelMoeda.setIcon(new ImageIcon(img));
        }
        labelMoeda.setBounds(largura - 1490, altura - 87, 30, 30);
        panelInventario.add(labelMoeda, Integer.valueOf(2));

        //Quantidade de moedas no inventário
        totalMoedas = new JLabel(inventario.getMoedasInventarioString());
        totalMoedas.setFont(fonte);
        totalMoedas.setForeground(new Color(40, 40, 40));
        totalMoedas.setBounds(largura - 1450, altura - 120, 189, 95);
        panelInventario.add(totalMoedas, Integer.valueOf(2));

        //Quantidade máxima de Coringas e Quantidade de Coringas atuais
        quantidadeCoringas = new JLabel("(" + inventario.getQtdCoringas() + "/" + inventario.getMaxCoringas() + ")");
        quantidadeCoringas.setFont(fonte);
        quantidadeCoringas.setForeground(new Color(40, 40, 40));
        quantidadeCoringas.setBounds(largura - 1490, altura - 150, 189, 95);
        panelInventario.add(quantidadeCoringas, Integer.valueOf(2));

        //Caixinha que fica embaixo da qtd de moedas e de coringas
        labelTextHolder = new JLabel();
        carregarImagem();
        labelTextHolder.setBounds(largura - 1500, altura - 150, 200, 100);
        panelInventario.add(labelTextHolder, Integer.valueOf(1));

        //Botão voltar do inventário
        fechar = new BotoesGUI("inventario/", 50, 50, 0);
        fechar.getBotao().addActionListener(e -> setVisible(false));
        fechar.getBotao().setBounds(largura - 150, altura - 100, 50, 50);
        fechar.getBotao().setFocusPainted(false);
        panelInventario.add(fechar.getBotao());

        //Botão de ajuda do inventário
        ajuda = new BotoesGUI("inventario/", 50, 50, 1);
        ajuda.getBotao().addActionListener(e -> setVisible(false));
        ajuda.getBotao().setBounds(largura - 150, altura - 900, 50, 50);
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

    private void carregarImagem() {
        String caminhoImagem = "/assets/images/mesaPrincipal/textholder.png";
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            labelTextHolder.setIcon(new ImageIcon(img));
        }
    }
}
