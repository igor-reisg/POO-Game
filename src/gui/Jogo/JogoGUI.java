package gui.Jogo;

import java.awt.*;
import javax.swing.*;

import gui.*;
import gui.Loja.LojaGUI;
import gui.Menu.MenuGUI;
import modelos.Jogo.Boss;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;
import modelos.Jogo.Pote;
import modelos.Loja.Loja;

public class JogoGUI extends JPanel {
    private final JanelaGUI app;
    private final Jogo jogo;
    private final Dimension tamanhoTela;

    private final PoteGUI pote;
    private final RoundCounterGUI contadorDeRound;
    private final MesaGUI mesa;
    private final CartasPanel[] cartasJogador, cartasInimigo;
    private final IconeGUI jogadorIcon, inimigoIcon;
    private final VidaGUI jogadorHP, inimigoHP;
    private final BotoesGUI pause, inicio, check, fold;
    private final Inventario inventario;
    private JPanel painelOponenteDerrotado;
    private JPanel painelDerrota;
    private String personagem;
    private String nome;

    private final BackgroundPanel background;

    private final String[] faseBackgrounds = {
            "/assets/images/background/pattern1.png",
            "/assets/images/background/pattern2.png",
            "/assets/images/background/pattern3.png",
            "/assets/images/background/pattern4.png",
            "/assets/images/background/pattern5.png"
    };

    public JogoGUI(JanelaGUI app, Jogo jogo, String personagem, String nome) {
        this.app = app;
        this.jogo = jogo;
        this.personagem = personagem;
        this.nome = nome;
        this.tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();

        setLayout(new BorderLayout());

        // Background
        background = new BackgroundPanel("/assets/images/background/pattern1.png");
        background.setLayout(null);
        add(background, BorderLayout.CENTER);
        atualizarBackgroundPorFase();

        // Vida do jogador
        jogadorHP = new VidaGUI(jogo.getJogador().getVida(), 1, jogo.getPote(), jogo);
        Dimension vidaJogadorSize = jogadorHP.getPreferredSize();
        jogadorHP.setBounds(
                tamanhoTela.width - vidaJogadorSize.width,
                tamanhoTela.height - vidaJogadorSize.height - 100,
                vidaJogadorSize.width,
                vidaJogadorSize.height
        );
        background.add(jogadorHP);

        // Vida do inimigo
        inimigoHP = new VidaGUI(jogo.getInimigo().getVida(), 2, jogo.getPote(), jogo);
        Dimension vidaAdversarioSize = inimigoHP.getPreferredSize();
        inimigoHP.setBounds(0, 0, vidaAdversarioSize.width, vidaAdversarioSize.height);
        background.add(inimigoHP);

        // Pote
        pote = new PoteGUI(jogo.getPote());
        Dimension poteSize = pote.getPreferredSize();
        pote.setBounds(0, (tamanhoTela.height - poteSize.height) / 2, poteSize.width, poteSize.height);
        background.add(pote);

        jogo.notificarGUIpronta();


        // Ícone do jogador
        jogadorIcon = new IconeGUI(personagem, nome, false, null);
        Dimension jogadorIconSize = jogadorIcon.getPreferredSize();
        jogadorIcon.setBounds(0, tamanhoTela.height - jogadorIconSize.height, jogadorIconSize.width, jogadorIconSize.height);
        background.add(jogadorIcon);
        // Ícone do adversário
        inimigoIcon = new IconeGUI(
                jogo.getInimigo().getPerfil().getImagemPath(),
                jogo.getInimigo().getPerfil().getNome(),
                jogo.getInimigo().getPerfil().isBoss(),
                jogo.getInimigo().getPerfil().getImagensBoss()
        );
        Dimension inimigoIconSize = inimigoIcon.getPreferredSize();
        inimigoIcon.setBounds(tamanhoTela.width - inimigoIconSize.width, 0, inimigoIconSize.width, inimigoIconSize.height);
        background.add(inimigoIcon);

        // Cartas do jogador
        cartasJogador = new CartasPanel[2];
        for (int i = 0; i < 2; i++) {
            cartasJogador[i] = new CartasPanel(jogo.getJogador().getMao()[i]);
            cartasJogador[i].setBounds(
                    jogadorIcon.getWidth() + cartasJogador[i].getWidth() * (1 + i) + 20,
                    tamanhoTela.height - cartasJogador[i].getHeight(),
                    cartasJogador[i].getWidth(),
                    cartasJogador[i].getHeight()
            );
            background.add(cartasJogador[i]);
        }

        // Cartas do inimigo
        cartasInimigo = new CartasPanel[2];
        for (int i = 0; i < 2; i++) {
            cartasInimigo[i] = new CartasPanel(jogo.getInimigo().getMao()[i]);
            cartasInimigo[i].setBounds(
                    tamanhoTela.width - inimigoIcon.getWidth() - cartasInimigo[i].getWidth() * (1 + i) - 130,
                    0,
                    cartasInimigo[i].getWidth(),
                    cartasInimigo[i].getHeight()
            );
            background.add(cartasInimigo[i]);
        }

        // Painel de oponente derrotado
        criarPainelOponenteDerrotado();

        // Mesa
        mesa = new MesaGUI(jogo.getMesa());
        Dimension mesaSize = mesa.getPreferredSize();
        mesa.setBounds(
                (tamanhoTela.width - mesaSize.width) / 2,
                (tamanhoTela.height - mesaSize.height) / 2,
                mesaSize.width,
                mesaSize.height
        );
        mesa.setOpaque(false);
        background.add(mesa);


        // Contador de Round
        contadorDeRound = new RoundCounterGUI();
        Dimension contadorDeRoundSize = contadorDeRound.getPreferredSize();
        contadorDeRound.setBounds((tamanhoTela.width - contadorDeRoundSize.width) / 2, 0,
                contadorDeRoundSize.width, contadorDeRoundSize.height);
        background.add(contadorDeRound);

        // Botão Pause
        pause = new BotoesGUI("jogo/pause", 50, 50, 0);
        pause.setBotao(pause.getBotao());
        pause.add(pause.getBotao());
        pause.setBounds(tamanhoTela.width - 50, (tamanhoTela.height - 50) / 2 - 50, 50, 50);
        pause.setOpaque(false);
        background.add(pause);

        inventario = new Inventario();
        app.trocarTela(new LojaGUI(
                app,
                inventario,
                new Loja(inventario),
                jogo.getinimigoNaFase(),
                jogo,  // instância atual do jogo
                this.personagem,
                this.nome
        ));

        // Botão Início
        inicio = new BotoesGUI("jogo/pause", 50, 50, 0);
        inicio.setBotao(inicio.getBotao());
        inicio.add(inicio.getBotao());
        inicio.setBounds(tamanhoTela.width - 50, (tamanhoTela.height - 50) / 2, 50, 50);
        inicio.setOpaque(false);
        background.add(inicio);

        inicio.getBotao().addActionListener(e -> app.trocarTela(new MenuGUI(app)));

        // Botão Check
        check = new BotoesGUI("jogo/check", 84, 42, 0);

        check.setEscalaX(5);
        check.setEscalaY(1.2);
        check.setBotao(check.getBotao());
        check.add(check.getBotao());
        check.setBounds(
                tamanhoTela.width - vidaJogadorSize.width,
                (int) (tamanhoTela.height - 84 * 1.3),
                42 * 5,
                (int) (84 * 1.3)
        );
        check.setOpaque(false);
        background.add(check);

        check.getBotao().addActionListener(e -> {
            jogo.getJogador().escolhaDaJogada(1);
            jogo.registrarEscolhaJogador(1);
        });




        // Botão Fold
        fold = new BotoesGUI("jogo/fold", 84, 42, 0);
        fold.setEscalaX(5);
        fold.setEscalaY(1.2);
        fold.setBotao(fold.getBotao());
        fold.add(fold.getBotao());
        fold.setOpaque(false);
        fold.setBounds(
                tamanhoTela.width - vidaJogadorSize.width + (int) (42 * 5.5),
                (int) (tamanhoTela.height - 84 * 1.3),
                42 * 5,
                (int) (84 * 1.3)
        );
        background.add(fold);

        fold.getBotao().addActionListener(e -> {
            jogo.getJogador().escolhaDaJogada(0);
            jogo.registrarEscolhaJogador(0);
        });

        jogo.setOnNovaRodada(() -> SwingUtilities.invokeLater(this::atualizarTudo));
    }

    private void criarPainelDerrota() {
        painelDerrota = new JPanel(new GridBagLayout());
        painelDerrota.setBackground(new Color(0, 0, 0, 200)); // Fundo semi-transparente
        painelDerrota.setBounds(0, 0, tamanhoTela.width, tamanhoTela.height);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Título
        JLabel titulo = new JLabel("DERROTA!", SwingConstants.CENTER);
        titulo.setForeground(new Color(255, 50, 50));
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        painelDerrota.add(titulo, gbc);

        // Mensagem
        JLabel mensagem = new JLabel("Você perdeu toda sua vida...", SwingConstants.CENTER);
        mensagem.setForeground(Color.WHITE);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 24));
        painelDerrota.add(mensagem, gbc);

        // Botão Tentar Novamente
        JButton btnTentar = new JButton("TENTAR NOVAMENTE");
        btnTentar.setFont(new Font("Arial", Font.BOLD, 24));
        btnTentar.setBackground(new Color(70, 70, 70));
        btnTentar.setForeground(Color.WHITE);
        btnTentar.setFocusPainted(false);
        btnTentar.setPreferredSize(new Dimension(300, 60));

        btnTentar.addActionListener(e -> {
            jogo.reiniciarFase();
            background.remove(painelDerrota);
            atualizarTudo();
        });
        painelDerrota.add(btnTentar, gbc);

        // Botão Menu Principal
        JButton btnMenu = new JButton("MENU PRINCIPAL");
        btnMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnMenu.setBackground(new Color(70, 70, 70));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusPainted(false);
        btnMenu.setPreferredSize(new Dimension(300, 60));

        btnMenu.addActionListener(e -> {
            app.trocarTela(new MenuGUI(app));
        });
        painelDerrota.add(btnMenu, gbc);
    }

    private void criarPainelOponenteDerrotado() {
        painelOponenteDerrotado = new JPanel(new GridBagLayout());
        painelOponenteDerrotado.setOpaque(false);
        painelOponenteDerrotado.setBounds(0, 0, tamanhoTela.width, tamanhoTela.height);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Mensagem de vitória
        JLabel lblMensagem = new JLabel("Oponente Derrotado!", SwingConstants.CENTER);
        lblMensagem.setForeground(Color.WHITE);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 36));
        painelOponenteDerrotado.add(lblMensagem, gbc);

        // Botão de ação
        JButton btnAcao = new JButton(jogo.temProximoOponente() ? "Próximo Oponente" : "Voltar ao Menu");
        btnAcao.setFont(new Font("Arial", Font.BOLD, 24));
        btnAcao.setPreferredSize(new Dimension(300, 60));

        btnAcao.addActionListener(e -> {
            if((jogo.getinimigoNaFase())%3 == 0){
                app.trocarTela(new LojaGUI(
                        app,
                        inventario,
                        new Loja(inventario),
                        jogo.getinimigoNaFase(),
                        jogo,  // instância atual do jogo
                        this.personagem,
                        this.nome
                ));
            }
            if (jogo.temProximoOponente()) {
                jogo.avancarParaProximoOponente();
                atualizarInimigoGUI();
            } else {
                app.trocarTela(new MenuGUI(app));
            }
            background.remove(painelOponenteDerrotado);
            background.revalidate();
            background.repaint();
        });

        painelOponenteDerrotado.add(btnAcao, gbc);
    }

    private void mostrarTelaOponenteDerrotado() {
        background.add(painelOponenteDerrotado);
        background.setComponentZOrder(painelOponenteDerrotado, 0);
        background.revalidate();
        background.repaint();
    }

    private void atualizarInimigoGUI() {
        inimigoIcon.setImagem(jogo.getInimigo().getPerfil().getImagemPath());
        inimigoIcon.setNome(jogo.getInimigo().getPerfil().getNome());

        // Atualiza estado do Boss se necessário
        if (jogo.getInimigo() instanceof Boss) {
            Boss boss = (Boss) jogo.getInimigo();
            inimigoIcon.atualizarEstadoBoss(boss.getEstadoHP());

            // Mostrar coringa do boss
           //mostrarCoringaBoss(boss.getCoringa());
        }

        //inimigoHP.vidaAlterada(jogo.getInimigo().getVidaAtual());
        inimigoIcon.revalidate();
        inimigoIcon.repaint();
    }

    private void atualizarContadorRodadas() {
        int estado;
        if (jogo.getInimigo().getPerfil().isBoss()) {
            estado = 2; // Boss
        } else if (jogo.getinimigoNaFase() == 0) {
            estado = 0; // Primeiro inimigo
        } else {
            estado = 1; // Segundo inimigo
        }
        contadorDeRound.updateRoundState(estado);
    }

    private void atualizarRoundCounter() {
        int estado = jogo.getRoundState();
        contadorDeRound.updateRoundState(estado);
    }

    private void avancarParaProximoOponente() {
        jogo.avancarParaProximoOponente();
        atualizarRoundCounter();
        atualizarInimigoGUI();
        atualizarBackgroundPorFase();
    }

    // Quando reiniciar após derrota:
    private void reiniciarFase() {
        jogo.reiniciarFase();
        contadorDeRound.reset();
        atualizarTudo();
    }

    private void mostrarTelaDerrota() {
        if (painelDerrota == null) {
            criarPainelDerrota();
        }
        background.add(painelDerrota);
        background.setComponentZOrder(painelDerrota, 0);
        background.revalidate();
        background.repaint();
    }

    public void atualizarTudo() {
        // Verifica se o inimigo foi derrotado
        if (jogo.getJogador().getVidaAtual() <= 0) {
            mostrarTelaDerrota();
            return;
        }

        // Verifica vitória sobre inimigo
        if (jogo.getInimigo().getVidaAtual() <= 0) {
            mostrarTelaOponenteDerrotado();
            return;
        }

        // Atualiza elementos visuais
        atualizarRoundCounter();
        atualizarInimigoGUI();

        // Remove as cartas antigas
        for (CartasPanel cartaPanel : cartasJogador) {
            if (cartaPanel.getParent() == background) {
                background.remove(cartaPanel);
            }
        }

        for (CartasPanel cartaPanel : cartasInimigo) {
            if (cartaPanel.getParent() == background) {
                background.remove(cartaPanel);
            }
        }

        // Adiciona as novas cartas do jogador
        for (int i = 0; i < 2; i++) {
            cartasJogador[i] = new CartasPanel(jogo.getJogador().getMao()[i]);

            cartasJogador[i].setBounds(
                    jogadorIcon.getWidth() + cartasJogador[i].getWidth() * (1 + i) + 20,
                    tamanhoTela.height - cartasJogador[i].getHeight(),
                    cartasJogador[i].getWidth(),
                    cartasJogador[i].getHeight()
            );
            background.add(cartasJogador[i]);
        }

        // Adiciona as novas cartas do inimigo
        for (int i = 0; i < 2; i++) {
            cartasInimigo[i] = new CartasPanel(jogo.getInimigo().getMao()[i]);
            cartasInimigo[i].setBounds(
                    tamanhoTela.width - inimigoIcon.getWidth() - cartasInimigo[i].getWidth() * (1 + i) - 130,
                    0,
                    cartasInimigo[i].getWidth(),
                    cartasInimigo[i].getHeight()
            );
            background.add(cartasInimigo[i]);
        }

        // Atualiza mesa
        mesa.atualizarMesa(jogo.getMesa());

        this.revalidate();
        this.repaint();
    }

    public void atualizarBotoesAposta() {
        if (jogo.getPote().getQuantidade() > 0) {
            // Muda para botão de call
            check.setCaminhoBotao("jogo/call");
            check.getBotao().setIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/call0_0.png")));
            check.getBotao().setRolloverIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/call0_1.png")));
            check.getBotao().setPressedIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/call0_2.png")));
        } else {
            // Volta para botão de check
            check.setCaminhoBotao("jogo/check");
            check.getBotao().setIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/check0_0.png")));
            check.getBotao().setRolloverIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/check0_1.png")));
            check.getBotao().setPressedIcon(new ImageIcon(getClass().getResource("/assets/images/botoes/jogo/check0_2.png")));
            // Configure os ícones originais do check
        }
        check.revalidate();
        check.repaint();
    }

    private void atualizarBackgroundPorFase() {
        int fase = jogo.getFaseAtual() - 1; // Convertendo para índice 0-based
        if (fase >= 0 && fase < faseBackgrounds.length) {
            background.setImagem(faseBackgrounds[fase]);
        }
    }


    public String getImagem(){ return personagem;}
    public String getNome(){ return nome;}
    public String getPersonagem() {
        return personagem;
    }


}