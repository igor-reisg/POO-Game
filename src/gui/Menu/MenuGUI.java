package gui.Menu;

import gui.*;
import gui.Jogo.*;
import gui.Loja.LojaGUI;
import modelos.Jogo.CriarPersona;
import modelos.Jogo.Jogo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class MenuGUI extends JPanel implements ActionListener {
    private final JanelaGUI app;
    private final String caminhoBackground = "/assets/images/background/pattern2.png";
    private BotoesGUI[] BotoesMenu;
    private CriarPersona CriarPersona;
    private CreditosGUI creditosGUI;
    private Font fonte;
    private final String caminhoFonte = "/assets/fonts/RetroGaming.ttf";
    BotoesGUI fechar;
    private BotoesGUI botaoMute;
    private boolean somLigado = true;

    public MenuGUI(JanelaGUI app) {
        this.app = app;

        this.creditosGUI = null;

        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Criação do logo
        LogoMenu logo = new LogoMenu();
        logo.setPreferredSize(new Dimension(1100, 800));
        logo.setLayout(null);
        logo.setOpaque(false);

        // Criação dos botões
        BotoesMenu = new BotoesGUI[5];
        for (int i = 0; i < BotoesMenu.length; i++) {
            BotoesMenu[i] = new BotoesGUI("menu/", i < 3 ? 95 : 66, i < 3 ? 189 : 66, i);
            BotoesMenu[i].getBotao().addActionListener(this);
        }

        // Painel inferior (botões principais)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panelInferior.setOpaque(false);
        for (int i = 0; i < 3; i++) {
            panelInferior.add(BotoesMenu[i].getBotao());
        }

        // Painel superior (botões auxiliares)
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        panelSuperior.setOpaque(false);
        for (int i = 3; i < 5; i++) {
            panelSuperior.add(BotoesMenu[i].getBotao());
        }

        // Painel principal do menu
        JPanel menuPainel = new JPanel(new BorderLayout());
        menuPainel.setOpaque(false);
        menuPainel.add(panelSuperior, BorderLayout.NORTH);
        menuPainel.add(logo, BorderLayout.CENTER);
        menuPainel.add(panelInferior, BorderLayout.SOUTH);

        // Background
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(menuPainel, BorderLayout.CENTER);

        add(background, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BotoesMenu[0].getBotao()) {
            if (CriarPersona == null) {
                CriarPersona = new CriarPersona(app);
            }
            app.trocarTela(CriarPersona);
        }
        else if (e.getSource() == BotoesMenu[1].getBotao()) {
            mostrarPainelOpcoes();
        }
        else if (e.getSource() == BotoesMenu[2].getBotao()) {
            if (creditosGUI == null) {
                creditosGUI = new CreditosGUI(app);
            }
            app.trocarTela(creditosGUI);
        }
        else if (e.getSource() == BotoesMenu[3].getBotao()) {
            JOptionPane.showMessageDialog(this, "Ajuda - Instruções do jogo");
        }
        else if (e.getSource() == BotoesMenu[4].getBotao()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair do jogo?", "Sair",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void mostrarPainelOpcoes() {
        // 1. Configuração inicial
        configurarAparenciaDoSlider();

        // 2. Carrega a fonte
        try {
            InputStream caminho = getClass().getResourceAsStream(caminhoFonte);
            fonte = Font.createFont(Font.TRUETYPE_FONT, caminho).deriveFont(20f);
        } catch (Exception e) {
            fonte = new Font("Arial", Font.PLAIN, 20);
            System.out.println("Erro ao carregar fonte: " + e);
        }

        // 3. Painel principal com fundo
        JPanel painelOpcoes = new JPanel() {
            private Image backgroundImage;

            {
                // Carrega a imagem no bloco de inicialização
                try {
                    URL imageUrl = getClass().getResource("/assets/images/inventario/inventario.png");
                    if (imageUrl != null) {
                        backgroundImage = new ImageIcon(imageUrl).getImage();
                    } else {
                        System.err.println("Imagem não encontrada!");
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar imagem: " + e.getMessage());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        painelOpcoes.setLayout(null);
        painelOpcoes.setPreferredSize(new Dimension(900, 600));
        painelOpcoes.setOpaque(false);

        // 4. Componentes
        JLabel labelVolume = new JLabel("Volume Música");
        labelVolume.setFont(fonte);
        labelVolume.setForeground(new Color (40, 40, 40));
        labelVolume.setBounds(350, 100, 300, 30);

        JSlider sliderVolume = criarSliderComImagens();
        sliderVolume.setBounds(300, 150, 300, 50);

        //Botão voltar do inventário
        fechar = new BotoesGUI("opcoes/", 50, 50, 2);
        fechar.getBotao().addActionListener(e -> app.getGlassPane().setVisible(false));
        fechar.getBotao().setBounds(800, 50, 50, 50);

        //Botao de Mutar musica
        JLabel labelMuteMusica = new JLabel("Mute Música");
        labelMuteMusica.setFont(fonte);
        labelMuteMusica.setForeground(new Color (40, 40, 40));
        labelMuteMusica.setBounds(365, 300, 300, 30);

        criarBotaoMute(painelOpcoes);

        // 5. Adiciona componentes
        painelOpcoes.add(labelVolume);
        painelOpcoes.add(labelMuteMusica);
        painelOpcoes.add(sliderVolume);
        painelOpcoes.add(fechar.getBotao());
        painelOpcoes.add(botaoMute.getBotao());

        // 6. Mostra o painel
        TransparenteGUI transparente = new TransparenteGUI(painelOpcoes);
        app.setGlassPane(transparente);
        transparente.setVisible(true);
    }

    private void criarBotaoMute(JPanel painelContainer) {
        String caminhoBase = somLigado ? "opcoes/som_ligado/" : "opcoes/som_desligado/";

        botaoMute = new BotoesGUI(caminhoBase, 60, 60, 1);

        botaoMute.getBotao().addActionListener(e -> {
            somLigado = !somLigado;
            app.setVolumeMusica(somLigado ? 1.0f : 0.0f);
            atualizarBotaoMute(painelContainer);
        });

        botaoMute.getBotao().setBounds(410, 350, 60, 60);
        painelContainer.add(botaoMute.getBotao());
    }

    private void atualizarBotaoMute(JPanel painelContainer) {
        String novoCaminho = somLigado ? "opcoes/som_ligado/" : "opcoes/som_desligado/";

        painelContainer.remove(botaoMute.getBotao());
        botaoMute = new BotoesGUI(novoCaminho, 60, 60, 1);
        botaoMute.getBotao().addActionListener(e -> {
            somLigado = !somLigado;
            app.setVolumeMusica(somLigado ? 1.0f : 0.0f);
            atualizarBotaoMute(painelContainer);
        });
        botaoMute.getBotao().setBounds(410, 350, 60, 60);
        painelContainer.add(botaoMute.getBotao());
        painelContainer.revalidate();
        painelContainer.repaint();
    }

    private void configurarAparenciaDoSlider() {
        try {
            Image thumbImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/images/botoes/opcoes/deslizebotao.png")));
            Image trackImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/images/botoes/opcoes/barrasom.png")));

            ImageIcon thumbIcon = new ImageIcon(thumbImg);
            ImageIcon trackIcon = new ImageIcon(trackImg);

            UIManager.put("Slider.horizontalThumbIcon", thumbIcon);
            UIManager.put("Slider.horizontalTrackIcon", trackIcon);
            UIManager.put("Slider.thumbSize", new Dimension(thumbIcon.getIconWidth(), thumbIcon.getIconHeight()));
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens do slider: " + e.getMessage());
            UIManager.put("Slider.horizontalThumbIcon", UIManager.get("Slider.horizontalThumbIcon"));
            UIManager.put("Slider.horizontalTrackIcon", UIManager.get("Slider.horizontalTrackIcon"));
        }
    }

    private JSlider criarSliderComImagens() {
        final int LARGURA_SLIDER = 300;
        final int ALTURA_SLIDER = 50;
        final int ALTURA_TRACK = 6;
        final int TAMANHO_THUMB = 20;

        JSlider slider = new JSlider(0, 100, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Image track = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/images/botoes/opcoes/barrasom.png"))).getImage();
                int yTrack = (getHeight() - ALTURA_TRACK) / 2;
                g.drawImage(track, 0, yTrack, getWidth(), ALTURA_TRACK, null);

                Image thumb = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/images/botoes/opcoes/deslizebotao.png"))).getImage();
                int xThumb = (int) ((getWidth() - TAMANHO_THUMB) * ((double)getValue() / (getMaximum() - getMinimum())));
                int yThumb = (getHeight() - TAMANHO_THUMB) / 2;
                g.drawImage(thumb, xThumb, yThumb, TAMANHO_THUMB, TAMANHO_THUMB, null);
            }
        };

        slider.setBounds(200, 200, LARGURA_SLIDER, ALTURA_SLIDER);
        slider.setOpaque(false);

        slider.addChangeListener(e -> {
            slider.repaint();
            app.setVolumeMusica(slider.getValue() / 100f);
        });

        return slider;
    }
}