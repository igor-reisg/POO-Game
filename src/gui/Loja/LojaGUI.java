package gui.Loja;

import gui.BackgroundPanel;
import gui.BotoesGUI;
import gui.JanelaGUI;
import gui.Jogo.CoringasGUI;
import gui.Jogo.InventarioGUI;
import gui.Jogo.JogoGUI;
import gui.StaticBackgroundPanel;
import modelos.Cartas.Coringa;
import modelos.Cartas.CoringaReader;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;
import modelos.Loja.MesaLoja;
import modelos.Loja.Sacola;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LojaGUI extends JPanel {
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    String caminhoCoringas = "/assets/data/coringas.json";
    int locationX = 100;
    int locationY = 100;
    int largura = 176;
    int altura = 248;
    private Dimension tamanhoTela;
    MesaLojaGUI mesa;
    SacolaGUI sacola;
    private StaticBackgroundPanel panelInventario;

    public LojaGUI(JanelaGUI app, Inventario inventario) {
        this.app = app;
        tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = tamanhoTela.width;
        int alturaTela = tamanhoTela.height;

        setLayout(new BorderLayout());

        // Painel em camadas
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(larguraTela, alturaTela));
        add(layeredPane, BorderLayout.CENTER);

        // Background
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.setBounds(0, 0, larguraTela, alturaTela);
        background.setLayout(null);
        layeredPane.add(background, Integer.valueOf(0));

        // Mesa c/ Coringas
        mesa = new MesaLojaGUI(new MesaLoja());
        Dimension mesaSize = mesa.getPreferredSize();
        mesa.setBounds((int)(larguraTela / 2 - mesaSize.getWidth() / 2), (int)(alturaTela / 2 - mesaSize.getHeight() / 2), mesaSize.width, mesaSize.height);
        mesa.setOpaque(false);
        background.add(mesa, Integer.valueOf(1));

        // Carrinho p/ comprar Coringas
        sacola = new SacolaGUI(new Sacola());
        Dimension sacolaSize = sacola.getPreferredSize();
        sacola.setBounds((larguraTela - sacolaSize.width - 30), (alturaTela - sacolaSize.height - 135), sacolaSize.width, sacolaSize.height);
        sacola.setOpaque(false);
        background.add(sacola, Integer.valueOf(1));

        // Inventário GUI sobre tudo
        InventarioGUI inventarioGUI = new InventarioGUI(inventario);
        //inventarioGUI.setBounds(0, 0, larguraTela, alturaTela);
        inventarioGUI.setOpaque(false);
        inventarioGUI.setVisible(false);
        layeredPane.add(inventarioGUI, Integer.valueOf(5));

        // Botões da loja (coluna 1)
        BotoesLoja = new BotoesGUI[4];
        for (int i = 0; i < 3; i++) {
            BotoesLoja[i] = new BotoesGUI("loja/", 95, 189, i);
            JButton botaoLoja = BotoesLoja[i].getBotao();
            botaoLoja.setBounds(larguraTela - 220, alturaTela - (110 * (3 - i)), 189, 95);
            layeredPane.add(botaoLoja, Integer.valueOf(1));
        }

        // Botões da loja (coluna 2)
        BotoesLoja[3] = new BotoesGUI("loja/", 95, 189, 3);
        JButton botaoLoja = BotoesLoja[3].getBotao();
        botaoLoja.setBounds(larguraTela - 440, alturaTela - 110, 189, 95);
        layeredPane.add(botaoLoja, Integer.valueOf(1));

        //tem que arrumar dps pra avançar o game e não iniciar um novo
        BotoesLoja[1].getBotao().addActionListener(e -> app.trocarTela(new JogoGUI(app, new Jogo())));

        BotoesLoja[2].getBotao().addActionListener(e -> {
            inventarioGUI.mostrarCoringas();
            inventarioGUI.setVisible(true);
        });

        setVisible(true);
    }

    public SacolaGUI getSacolaGUI() {
        return sacola;
    }
}
