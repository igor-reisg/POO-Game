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
    private final String personagem;  // Adicione estas variáveis
    private final String nome;
    private final Jogo jogoExistente;
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    private Dimension tamanhoTela;
    private MesaLojaGUI mesa;
    private SacolaGUI sacolaGUI;
    private Loja loja;
    private final int inimigoAtual;
    private MesaLoja mesaLoja;
    private Sacola sacola;
    private LojaGUI lojaGUI;
    private JLabel placaLabel;

    public LojaGUI(JanelaGUI app, Inventario inventario, Loja loja, int inimigoAtual, Jogo jogoExistente, String personagem, String nome) {
        
        this.app = app;
        this.loja = loja;
        this.personagem = personagem;
        this.nome = nome;
        this.inimigoAtual = inimigoAtual;
        this.jogoExistente = jogoExistente;
        this.mesaLoja = new MesaLoja();
        this.sacola = new Sacola();

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

        //Sacola
        sacolaGUI = new SacolaGUI(sacola);
        Dimension sacolaSize = sacolaGUI.getPreferredSize();
        sacolaGUI.setBounds((larguraTela - sacolaSize.width - 30), (alturaTela - sacolaSize.height - 135), sacolaSize.width, sacolaSize.height);
        sacolaGUI.setOpaque(false);
        background.add(sacolaGUI, Integer.valueOf(0));

        //Placa loja
        placaLabel = new JLabel();
        carregarImagem();
        placaLabel.setBounds(larguraTela - 440, alturaTela - 300, 168, 132);
        background.add(placaLabel, Integer.valueOf(1));

        // Mesa com Coringas
        mesa = new MesaLojaGUI(mesaLoja, sacola, sacolaGUI);
        Dimension mesaSize = mesa.getPreferredSize();
        int posX = (larguraTela - mesaSize.width) / 2;
        int posY = (alturaTela - mesaSize.height) / 2;
        mesa.setBounds(posX, posY, mesaSize.width, mesaSize.height);
        mesa.setOpaque(false);
        layeredPane.add(mesa, Integer.valueOf(2));

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

        // Botão 0 - Comprar Coringas da Sacola
        BotoesLoja[0].getBotao().addActionListener(e -> {
            if (loja.possivelComprar(sacola)) {
                loja.comprarItens(sacola);
                System.out.println("Compra realizada com sucesso!");

                sacolaGUI.atualizarPreco();
                inventarioGUI.atualizarDisplay();

                mesa.removerCartasCompradas();

            } else {
                System.out.println("Não foi possível comprar. Verifique seu dinheiro ou espaço no inventário.");
            }
        });

        // Botão 1 - Avançar jogo (pode ajustar depois para rodadas reais)
        BotoesLoja[1].getBotao().addActionListener(e -> {
            app.trocarTela(new JogoGUI(app, jogoExistente, personagem, nome));
        });

        // Botão 2 - Mostrar Inventário
        BotoesLoja[2].getBotao().addActionListener(e -> {
            inventarioGUI.mostrarCoringas();
            inventarioGUI.setVisible(true);
            mesa.setHoverEnabled(false);
        });

        // Botão 3 - Atualizar loja
        BotoesLoja[3].getBotao().addActionListener(e -> {
            if (loja.possivelAtualizarLoja()) {
                loja.atualizarLoja();
                mesaLoja.gerarNovosCoringas();
                mesa.atualizarCartas();
                inventarioGUI.atualizarDisplay();
                System.out.println("Loja atualizada!");
            }
            else {
                System.out.println("Dinheiro insuficiente ou loja já atualizada nesta rodada.");
            }
        });

        setVisible(true);
    }

    public MesaLojaGUI getMesa() {
        return mesa;
    }

    private void carregarImagem() {
        String caminhoImagem = "/assets/images/mesaPrincipal/placaloja.png";
        URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(168, 132, Image.SCALE_SMOOTH);
            placaLabel.setIcon(new ImageIcon(img));
        }
    }

    public SacolaGUI getSacolaGUI() {
        return sacolaGUI;
    }
}
