
package modelos.Jogo;

import java.util.*;

import gui.Jogo.VidaGUI;
import gui.Jogo.PoteGUI;
import modelos.Cartas.*;
import gui.Jogo.JogoGUI;
import javax.swing.SwingUtilities;


public class Jogo {
    private List<PerfilInimigo> perfisInimigos;
    private JogoGUI jogoGUI;
    private int inimigoAtualIndex = 0;
    private Round rodada;
    private int etapaRodada = 0;
    private Baralho baralho;
    private Pote pote;
    private Jogador jogador;
    private Inimigo inimigo;
    private Mesa mesa;
    private int moeda;
    private int faseAtual = 1; // 1 a 5
    private int inimigoNaFase = 1;

    private boolean guiPronta = false;
    private boolean jogadorPronto = false;
    private boolean inimigoPronto = false;


    private int jogadaJogador;
    private int jogadaInimigo;

    private boolean fimRodada;

    private Runnable onNovaRodada;

    public void setJogoGUI(JogoGUI jogoGUI) {
        this.jogoGUI = jogoGUI;
    }


    public Jogo(int faseAtual) {
        carregarPerfisInimigos();
        rodada = new Round();
        jogador = new Jogador(null);
        if(faseAtual != 1){
            inimigo = new Inimigo(perfisInimigos.get(inimigoNaFase));
        } else {
            inimigo = new Inimigo(perfisInimigos.get(0));
        }
  // Primeiro inimigo
        baralho = new Baralho();
        pote = new Pote();
    }

    private void carregarPerfisInimigos() {
        perfisInimigos = new ArrayList<>();
        perfisInimigos.add(new PerfilInimigo(
                "Joao Palmeirense",
                "/assets/images/frames/framesEnemies/inimigo_1.png",
                5, 5, 50, 1500, false, null
        ));
        perfisInimigos.add(new PerfilInimigo(
                "Keeko Lindo",
                "/assets/images/frames/framesEnemies/inimigo_2.png",
                7, 3, 70, 1500, false, null
        ));

        String[] imagensBossDavi = {
                "/assets/images/frames/framesBoss/boss4_0.png", // vida cheia
                "/assets/images/frames/framesBoss/boss4_1.png", // 2/3 vida
                "/assets/images/frames/framesBoss/boss4_2.png"  // 1/3 vida
        };

        perfisInimigos.add(new PerfilInimigo(
                "David, o ruim.",
                "/assets/images/frames/framesBoss/boss4_0.png",
                9, 2, 90, 1500, true, imagensBossDavi
        ));

        perfisInimigos.add(new PerfilInimigo(
                "Ossamo Makoto",
                "/assets/images/frames/framesEnemies/inimigo_3.png",
                9, 2, 90, 1500, false, null
        ));
        perfisInimigos.add(new PerfilInimigo(
                "Tina Repentina",
                "/assets/images/frames/framesEnemies/inimigo_4.png",
                9, 2, 90, 1500, false, null
        ));

        String[] imagensBossAndre = {
                "/assets/images/frames/framesBoss/boss3_0.png", // vida cheia
                "/assets/images/frames/framesBoss/boss3_1.png", // 2/3 vida
                "/assets/images/frames/framesBoss/boss3_2.png"  // 1/3 vida
        };

        perfisInimigos.add(new PerfilInimigo(
                "Andrei, o curioso.",
                "/assets/images/frames/framesBoss/boss3_0.png",
                9, 2, 90, 1500, true, imagensBossAndre
        ));

        perfisInimigos.add(new PerfilInimigo(
                "Tona Repentona",
                "/assets/images/frames/framesEnemies/inimigo_5.png",
                9, 2, 90, 1500, false, null
        ));
        perfisInimigos.add(new PerfilInimigo(
                "Enrico CarroHomem",
                "/assets/images/frames/framesEnemies/inimigo_6.png",
                9, 2, 90, 1500, false, null
        ));

        String[] imagensBossTux = {
                "/assets/images/frames/framesBoss/boss2_0.png", // vida cheia
                "/assets/images/frames/framesBoss/boss2_1.png", // 2/3 vida
                "/assets/images/frames/framesBoss/boss2_2.png"  // 1/3 vida
        };

        perfisInimigos.add(new PerfilInimigo(
                "Truxs, o sumido",
                "/assets/images/frames/framesBoss/boss2_0.png",
                9, 2, 90, 1500, true, imagensBossTux
        ));

        perfisInimigos.add(new PerfilInimigo(
                "Senhor Vovô",
                "/assets/images/frames/framesEnemies/inimigo_7.png",
                9, 2, 90, 1500, false, null
        ));
        perfisInimigos.add(new PerfilInimigo(
                "Diba Madiba",
                "/assets/images/frames/framesEnemies/inimigo_8.png",
                9, 2, 90, 1500, false, null
        ));

        String[] imagensBossJulio = {
                "/assets/images/frames/framesBoss/boss1_0.png", // vida cheia
                "/assets/images/frames/framesBoss/boss1_1.png", // 2/3 vida
                "/assets/images/frames/framesBoss/boss1_2.png"  // 1/3 vida
        };

        perfisInimigos.add(new PerfilInimigo(
                "Juliano, o sabido",
                "/assets/images/frames/framesEnemies/boss1_0.png",
                9, 2, 90, 1500, true, imagensBossJulio
        ));
        perfisInimigos.add(new PerfilInimigo(
                "teste9",
                "/assets/images/frames/framesEnemies/inimigo_9.png",
                9, 2, 90, 1500, false, null
        ));
        perfisInimigos.add(new PerfilInimigo(
                "teste10",
                "/assets/images/frames/framesEnemies/inimigo_10.png",
                9, 2, 90, 1500, false, null
        ));

        String[] imagensBossNaka = {
                "/assets/images/frames/framesBoss/boss0_0.png", // vida cheia
                "/assets/images/frames/framesBoss/boss0_1.png", // 2/3 vida
                "/assets/images/frames/framesBoss/boss0_2.png"  // 1/3 vida
        };

        perfisInimigos.add(new PerfilInimigo(
                "Niko, o travado",
                "/assets/images/frames/framesBoss/boss0_0.png",
                9, 2, 90, 1500, true, imagensBossNaka
        ));

    }

    public void proximoInimigo() {
        inimigoAtualIndex++;
        if (inimigoAtualIndex < perfisInimigos.size()) {
            inimigo = new Inimigo(perfisInimigos.get(inimigoAtualIndex));
            novaRodada();
        } else {
            // Todos os inimigos foram derrotados
            finalizarJogo(true);
        }
    }



    public void setOnNovaRodada(Runnable callback) {
        this.onNovaRodada = callback;
    }

    public void notificarGUIpronta() {
        this.guiPronta = true;
        this.iniciarJogo(); // Só inicia o jogo depois que a GUI estiver pronta
    }

    private void iniciarJogo() {

        Random r = new Random();
        moeda = r.nextInt(2);

        jogador.setBlind(moeda);
        jogador.setBlind(1-moeda);
        novaRodada();
    }


    private void novaRodada() {
        Timer timer = new Timer();

        // 1. Inicializar baralho se necessário
        if (baralho == null) {
            baralho = new Baralho();
        }
        baralho.reiniciaBaralho();

        // 2. Inicializar mesa se necessário
        if (mesa == null) {
            mesa = new Mesa(baralho);
        } else {
            mesa.resetCartas();
        }

        // 3. Limpar mãos
        jogador.limpaCartas();
        inimigo.limpaCartas();

        // 4. Distribuir cartas
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        System.out.println("\n=== NOVA RODADA ===");
        System.out.println("Jogador: " + jogador.getVida().getVida() + " HP | Inimigo: " +
                inimigo.getVida().getVida() + " HP | Pote: " + pote.getQuantidade());

        etapaRodada = 0;
        fimRodada = false;

        timer.schedule(new TimerTask() { public void run() { jogador.revelaCarta(0); }}, 500);
        timer.schedule(new TimerTask() { public void run() { jogador.revelaCarta(1); }}, 700);

        aplicarBlinds();

        jogadorPronto = false;
        inimigoPronto = false;

        if (onNovaRodada != null) {
            onNovaRodada.run();
        }
    }

    private void aplicarBlinds() {
        int smallBlind = 100;
        int bigBlind = 200;

        if (jogador.getBlind() == 0) {
            // Jogador é small blind
            jogador.getVida().selecionarVida(smallBlind);
            jogador.getVida().setVida(jogador.getVida().getVida() - smallBlind);
            pote.adicionarApostaJogador(smallBlind);

            // Inimigo é big blind
            inimigo.getVida().selecionarVida(bigBlind);
            inimigo.getVida().setVida(inimigo.getVida().getVida() - bigBlind);
            pote.adicionarApostaInimigo(bigBlind);
        } else {
            // Jogador é big blind
            jogador.getVida().selecionarVida(bigBlind);
            jogador.getVida().setVida(jogador.getVida().getVida() - bigBlind);
            pote.adicionarApostaJogador(bigBlind);

            // Inimigo é small blind
            inimigo.getVida().selecionarVida(smallBlind);
            inimigo.getVida().setVida(inimigo.getVida().getVida() - smallBlind);
            pote.adicionarApostaInimigo(smallBlind);
        }

        // Rotacionar blinds
        jogador.setBlind(1 - jogador.getBlind());
        inimigo.setBlind(1 - inimigo.getBlind());
    }

    public void registrarEscolhaJogador(int escolha) {
        // Se houver aposta no pote e jogador der check, trata como call
        if (escolha == 1 ){ // Call
            jogadaJogador = 1;
            int valorCall = pote.getUltimaApostaInimigo() - pote.getUltimaApostaJogador();
            jogador.getVida().selecionarVida(valorCall + pote.getApostaJogador());
            pote.adicionarApostaJogador(valorCall);
            pote.adicionarApostaJogador(0); // Indica que o valor nao aumentou por parte do jogador, mas apenas completou
            jogador.getVida().setVida(jogador.getVida().getVida() - valorCall);
            if(valorCall == 0){
                System.out.println("Jogador deu check");
            } else{
                System.out.println("Jogador deu call: " + valorCall);
            }
        }
        jogadorPronto = true;
        registrarEscolhaInimigo();
    }
  public void registrarEscolhaJogador(int escolha, int valor) {
        if (escolha == 2) { // Raise
            jogador.getVida().selecionarVida( valor);
            jogador.getVida().setVida(jogador.getVida().getVida() - valor);
            pote.adicionarApostaJogador(valor);
        }
        jogadaJogador = escolha;
        jogadorPronto = true;
        registrarEscolhaInimigo();
    }

    private void registrarEscolhaInimigo() {
        inimigo.decidirJogada(pote, mesa, etapaRodada);
        jogadaInimigo = inimigo.getJogada();
        inimigoPronto = true;
        verificarEtapa();
    }

    private void verificarEtapa() {

        if (!jogadorPronto || !inimigoPronto) return;

        // Processa as jogadas atuais - se houver fold, já termina aqui
        processarRodada();

        // Se alguém foldou, não avança etapas - só prepara nova rodada
        if (jogadaJogador == 0 || jogadaInimigo == 0) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1000); // Pequeno delay para visualização
                    novaRodada();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return;
        }

        if (etapaRodada > 3) {
            System.err.println("[ERRO] Etapa inválida: " + etapaRodada);
            etapaRodada = 0;
            fimRodada = true;
            processarRodada();
            return;
        }

        if (jogadorPronto && inimigoPronto) {
            processarRodada();
            etapaRodada++;

            Timer timer = new Timer();
            if (etapaRodada == 1) {
                mesa.revelaCarta(0);
                timer.schedule(new TimerTask() { public void run() { mesa.revelaCarta(1); }}, 500);
                timer.schedule(new TimerTask() { public void run() { mesa.revelaCarta(2); }}, 1000);
            } else if (etapaRodada == 2) {
                mesa.revelaCarta(3);
            } else if (etapaRodada == 3) {
                mesa.revelaCarta(4);
                fimRodada = true;
            } else {
                timer.schedule(new TimerTask() {
                    public void run() {
                        SwingUtilities.invokeLater(() -> {
                            novaRodada();
                        });
                    }
                }, 1000);
            }

            jogadorPronto = false;
            inimigoPronto = false;
        }
    }

    private void processarRodada() {
        // Se alguém foldou, termina a rodada imediatamente sem mostrar mensagem
        if (jogadaJogador == 0 || jogadaInimigo == 0) {
            PokerLogica.Resultado resultado = (jogadaJogador == 0) ?
                    PokerLogica.Resultado.INIMIGO_VENCE :
                    PokerLogica.Resultado.JOGADOR_VENCE;

            finalizarRodada(resultado);
            return;
        }

        // Se chegou até aqui e é o fim da rodada, compara as mãos
        if (fimRodada) {
            inimigo.revelaCarta(0);
            inimigo.revelaCarta(1);

            PokerLogica.Resultado resultado = PokerLogica.avaliarRodada(
                    Arrays.asList(jogador.getMao()),
                    Arrays.asList(inimigo.getMao()),
                    Arrays.asList(mesa.getCartas())
            );

            finalizarRodada(resultado);
        }
    }

    private void processarApostas() {
        int apostaInimigo = inimigo.calcularAposta(pote, mesa, etapaRodada);
        pote.adicionarApostaInimigo(apostaInimigo);

    }

    private void finalizarRodada(PokerLogica.Resultado resultado) {
        System.out.println("\n=== RESULTADO DA RODADA ===");
        System.out.println("Jogador: " + jogador.getVida().getVida() + " HP");
        System.out.println("Inimigo: " + inimigo.getVida().getVida() + " HP");
        System.out.println("Pote: " + pote.getQuantidade());

        // Avalia as mãos para mostrar a jogada
        List<Carta> cartasJogador = Arrays.asList(jogador.getMao());
        List<Carta> cartasInimigo = Arrays.asList(inimigo.getMao());
        List<Carta> cartasMesa = Arrays.asList(mesa.getCartas());

        PokerLogica.MaoAvaliacao avaliacaoJogador = PokerLogica.avaliarMao(cartasJogador);
        PokerLogica.MaoAvaliacao avaliacaoInimigo = PokerLogica.avaliarMao(cartasInimigo);

        if (fimRodada && jogoGUI != null) {
            SwingUtilities.invokeLater(() -> {
                switch(resultado) {
                    case JOGADOR_VENCE:
                        int forcaJogador = determinarForcaMaoParaImagem(avaliacaoJogador.forca);
                        jogoGUI.mostrarMensagemJogada(true, forcaJogador);
                        break;

                    case INIMIGO_VENCE:
                        int forcaInimigo = determinarForcaMaoParaImagem(avaliacaoInimigo.forca);
                        jogoGUI.mostrarMensagemJogada(false, forcaInimigo);
                        break;

                    case EMPATE:
                        int forcaJ = determinarForcaMaoParaImagem(avaliacaoJogador.forca);
                        int forcaI = determinarForcaMaoParaImagem(avaliacaoInimigo.forca);
                        jogoGUI.mostrarMensagemJogada(true, forcaJ);

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    jogoGUI.mostrarMensagemJogada(false, forcaI);
                                });
                            }
                        }, 1500);
                        break;
                }
            });
        }}

    private void mostrarProximoOponente() {
        if (onNovaRodada != null) {
            onNovaRodada.run(); // Notifica a GUI para mostrar a tela de oponente derrotado
        }
    }

    private void prepararNovaRodada() {
        if (verificarFimDeJogo()) {
            finalizarJogo(jogador.getVidaAtual() > 0);
            return;
        }

        // Resetar estado antes de nova rodada
        mesa.resetCartas();
        pote.resetPote();

        // Agendar nova rodada com pequeno delay
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    // Distribui novas cartas apenas uma vez
                    baralho.reiniciaBaralho();
                    jogador.limpaCartas();
                    inimigo.limpaCartas();
                    jogador.recebeCarta(baralho);
                    inimigo.recebeCarta(baralho);

                    // Atualiza GUI
                    if (onNovaRodada != null) {
                        onNovaRodada.run();
                    }

                    // Revela cartas do jogador com delay
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            SwingUtilities.invokeLater(() -> jogador.revelaCarta(0));
                        }
                    }, 500);

                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            SwingUtilities.invokeLater(() -> jogador.revelaCarta(1));
                        }
                    }, 700);

                    aplicarBlinds();
                });
            }
        }, 1000); // Delay após mensagem
    }

    private void continuarAposMensagem() {
        // Verifica se o jogo acabou
        if (verificarFimDeJogo()) {
            finalizarJogo(jogador.getVidaAtual() > 0);
            return;
        }

        // Prepara nova rodada após um pequeno delay
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    mesa.resetCartas();
                    pote.resetPote();
                    novaRodada();
                });
            }
        }, 1000); // Pequeno delay antes de começar nova rodada
    }

    public boolean temProximoOponente() {
        return inimigoAtualIndex < perfisInimigos.size() - 1;
    }

    public void avancarParaProximoOponente() {
        inimigoNaFase++;
        inimigoAtualIndex++;

        // Resetar o pote ao mudar de oponente
        pote.resetPote();

        jogador.getVida().setVida(1500);
        inimigo.getVida().setVida(1500);

        if (inimigoNaFase > 3) { // Passou da fase (2 normais + 1 boss)
            faseAtual++;

            if (faseAtual > 5) {
                finalizarJogo(true);
                return;
            }
        }

        inimigo = new Inimigo(perfisInimigos.get(inimigoAtualIndex));
        novaRodada();
    }

    private boolean verificarFimDeJogo() {
        if (jogador.getVidaAtual() <= 0) {
            System.out.println("FIM DE JOGO - INIMIGO VENCEU!");
            return true;
        } else if (inimigo.getVidaAtual() <= 0) {
            System.out.println("FIM DE JOGO - JOGADOR VENCEU!");
            return true;
        }
        return false;
    }

    public void reiniciarFase() {
        int inicioFase = (faseAtual - 1) * 3; // Cada fase tem 3 inimigos (2 normais + 1 boss)
        inimigoAtualIndex = inicioFase;
        inimigo = new Inimigo(perfisInimigos.get(inimigoAtualIndex));

        // Resetar HP do jogador para o valor inicial ou outro lógica que preferir
        jogador.getVida().setVida(1500);

        novaRodada();
        inimigo.getVida().setVida(1500);
    }

    private void finalizarJogo(boolean jogadorVenceu) {

        System.out.println(jogadorVenceu ? "Você venceu o jogo!" : "Você perdeu o jogo!");

        // Encerra o jogo
        System.out.println("Fim do jogo!");
        //Só pra debug, vou mudar depois
    }

    public int getRoundState() {
        if (inimigoNaFase == 1) return 0; // Primeiro inimigo (roundcounter_1)
        if (inimigoNaFase == 2) return 1; // Segundo inimigo (roundcounter_2)
        if (inimigo.getPerfil().isBoss()) return 3; // Boss (roundcounter_3)
        return 3; // Completo (roundcounter_4) - não deve ocorrer normalmente
    }

    private int determinarForcaMaoParaImagem(int forca) {
        if (forca >= 800) return 8;      // Straight Flush
        if (forca >= 700) return 7;      // Quadra
        if (forca >= 600) return 6;      // Full House
        if (forca >= 500) return 5;      // Flush
        if (forca >= 400) return 4;      // Straight
        if (forca >= 300) return 3;      // Trinca
        if (forca >= 200) return 2;      // Dois Pares
        if (forca >= 100) return 1;      // Um Par
        return 0;                        // Carta Alta
    }


    public Round getRound(){ return rodada; }
    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Mesa getMesa() { return mesa; }
    public Pote getPote() { return pote; }
    public int getFaseAtual() {
        return faseAtual;
    }

    public int getinimigoNaFase() {
        return inimigoNaFase;
    }
}
