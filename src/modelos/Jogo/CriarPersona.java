package modelos.Jogo;

import gui.BackgroundPanel;
import gui.BotoesGUI;
import gui.JanelaGUI;
import gui.Jogo.CoringasGUI;
import gui.Jogo.JogoGUI;
import modelos.Cartas.Coringa;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class CriarPersona extends JPanel {
    private JogoGUI JogoGUI;
    private final JanelaGUI app;
    private Font fonte;
    private JLabel LabelFundo;
    private JLabel DigiteSeuNome;
    private JLabel CliquePersonagem;
    private JTextField CampoTexto;
    private final String caminhoBackground = "/assets/images/background/pattern5.png";
    private final String caminhoFundo = "/assets/images/inventario/inventario.png";
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    private BotoesGUI[] Personagens, Botoes;
    private Dimension tamanhoTela;
    private final String imagensPersonagens = "personagens/";


    public CriarPersona(JanelaGUI app) {
        this.app = app;
        setLayout(new BorderLayout());

        tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = tamanhoTela.width;
        int alturaTela = tamanhoTela.height;

        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(larguraTela, alturaTela));
        add(layeredPane, BorderLayout.CENTER);

        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.setBounds(0, 0, larguraTela, alturaTela);
        background.setLayout(null);
        layeredPane.add(background, Integer.valueOf(0));

        LabelFundo = new JLabel();
        URL url = getClass().getResource(caminhoFundo);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image img = original.getImage().getScaledInstance(1550, 950, Image.SCALE_SMOOTH);
            LabelFundo.setIcon(new ImageIcon(img));
        }
        LabelFundo.setBounds((larguraTela - 1550)/2, (alturaTela - 950)/2, 1550, 950);
        background.add(LabelFundo, Integer.valueOf(1));

        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(24f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 24);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        DigiteSeuNome = new JLabel("1. Digite aqui seu nome:");
        DigiteSeuNome.setForeground(new Color(40, 40, 40));
        DigiteSeuNome.setBounds(450,80, 600,70);
        DigiteSeuNome.setFont(fonte);
        LabelFundo.add(DigiteSeuNome, Integer.valueOf(2));

        CliquePersonagem = new JLabel("2. Clique no Personagem desejado:");
        CliquePersonagem.setForeground(new Color(40, 40, 40));
        CliquePersonagem.setBounds(450,250, 600,70);
        CliquePersonagem.setFont(fonte);
        LabelFundo.add(CliquePersonagem, Integer.valueOf(2));

        CampoTexto = new JTextField();
        CampoTexto.setForeground(new Color(40, 40, 40));
        CampoTexto.setBounds(450,150, 600,70);
        CampoTexto.setFont(fonte);
        LabelFundo.add(CampoTexto, Integer.valueOf(2));


        Personagens = new BotoesGUI[6];
        int espacamentoX = 400;
        int espacamentoY = 300;
        int xInicial = 435;
        int yInicial = 400;


        for (int i = 0; i < 6; i++) {
            Personagens[i] = new BotoesGUI(imagensPersonagens, 180, 255, i);
            JButton botaoLoja = Personagens[i].getBotao();

            int lin = i / 3;
            int col = i % 3;
            int posX = xInicial + col * espacamentoX;
            int posY = yInicial + lin * espacamentoY;

            botaoLoja.setBounds(posX, posY, 255, 180);
            layeredPane.add(botaoLoja, Integer.valueOf(2));

            int finalI = i;
            Personagens[i].getBotao().addActionListener(e -> {

                if (JogoGUI == null){
                    final String Imagem = Personagens[finalI].getCaminho() + finalI + "_0.png";
                    final String nome = CampoTexto.getText();
                    JogoGUI = new JogoGUI(app, new Jogo(1), Imagem, nome);
                }
                app.trocarTela(JogoGUI);
            });
        }
    }



}
