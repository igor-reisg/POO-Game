package gui.Loja;

import gui.BackgroundPanel;
import gui.BotoesGUI;
import gui.JanelaGUI;
import gui.Jogo.CoringasGUI;
import gui.Jogo.InventarioGUI;
import gui.Jogo.JogoGUI;
import gui.StaticBackgroundPanel;
import modelos.Cartas.Coringa;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;
import modelos.Loja.MesaLoja;
import modelos.Loja.Sacola;
import modelos.Loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class LojaGUI extends JPanel {
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    private Dimension tamanhoTela;
    private MesaLojaGUI mesa;
    private SacolaGUI sacola;
    private StaticBackgroundPanel panelInventario;
    private Loja loja;
    private MesaLoja mesaLoja;

    public LojaGUI(JanelaGUI app, Inventario inventario, Loja loja) {
        this.app = app;
        this.loja = loja;
        this.mesaLoja = new MesaLoja();
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

        // Mesa com Coringas
        mesa = new MesaLojaGUI(mesaLoja);
        Dimension mesaSize = mesa.getPreferredSize();
        int posX = (larguraTela - mesaSize.width) / 2;
        int posY = (alturaTela - mesaSize.height) / 2;
        mesa.setBounds(posX, posY, mesaSize.width, mesaSize.height);
        mesa.setOpaque(false);
        background.add(mesa, Integer.valueOf(1));

        //Sacola
        sacola = new SacolaGUI(new Sacola());
        Dimension sacolaSize = sacola.getPreferredSize();
        sacola.setBounds((larguraTela - sacolaSize.width - 30), (alturaTela - sacolaSize.height - 135), sacolaSize.width, sacolaSize.height);
        sacola.setOpaque(false);
        background.add(sacola, Integer.valueOf(1));

        // Inventário GUI
        InventarioGUI inventarioGUI = new InventarioGUI(inventario);
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

        // Botão atualizar (coluna 2)
        BotoesLoja[3] = new BotoesGUI("loja/", 95, 189, 3);
        JButton botaoLoja = BotoesLoja[3].getBotao();
        botaoLoja.setBounds(larguraTela - 440, alturaTela - 110, 189, 95);
        layeredPane.add(botaoLoja, Integer.valueOf(1));

        // Botão 1 - Avançar jogo (pode ajustar depois para rodadas reais)
        BotoesLoja[1].getBotao().addActionListener(e -> app.trocarTela(new JogoGUI(app, new Jogo())));

        // Botão 2 - Mostrar Inventário
        BotoesLoja[2].getBotao().addActionListener(e -> {
            inventarioGUI.mostrarCoringas();
            inventarioGUI.setVisible(true);
        });

        // Botão 3 - Atualizar loja
        BotoesLoja[3].getBotao().addActionListener(e -> {
            if (loja.possivelAtualizarLoja()) {
                loja.atualizarLoja();
                mesaLoja.gerarNovosCoringas();
                mesa.atualizarCartas();
            } else {
                BotoesLoja[3].getBotao().setEnabled(false); //TEM QUE ARRUMAR ESSA BOSTA, SE MUDAR A IMAGEM MANUALMENTE ESSA MERDA FICA BUGADA
                System.out.println("Não é possível atualizar a loja");
            }
        });

        setVisible(true);
    }

    public SacolaGUI getSacolaGUI() {
        return sacola;
    }
}
