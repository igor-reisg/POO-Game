
package modelos.Jogo;

import java.util.*;

import gui.Jogo.VidaGUI;
import gui.Jogo.PoteGUI;
import modelos.Cartas.*;
import javax.swing.SwingUtilities;


public class Jogo {
    private Round rodada;
    private int etapaRodada = 0;
    private Vida vida;
    private Baralho baralho;
    private Pote pote;
    private Jogador jogador;
    private Inimigo inimigo;
    private Mesa mesa;
    private VidaGUI vidaGUIJogador;
    private VidaGUI vidaGUIInimigo;
    private PoteGUI poteGUI;

    private boolean jogadorPronto = false;
    private boolean inimigoPronto = false;

    private int jogadaJogador;
    private int jogadaInimigo;

    private boolean fimRodada;

    private Runnable onNovaRodada;

    public void setVidaGUIs(VidaGUI vidaGUIJogador, VidaGUI vidaGUIInimigo) {
        this.vidaGUIJogador = vidaGUIJogador;
        this.vidaGUIInimigo = vidaGUIInimigo;
    }


    public Jogo(){
        rodada = new Round();
        jogador = new Jogador("Naka");
        inimigo = new Inimigo();
        pote = new Pote();
        iniciarJogo();
    }

    public void setOnNovaRodada(Runnable callback) {
        this.onNovaRodada = callback;
    }

    private void iniciarJogo() {
        Random r = new Random();
        int moeda = r.nextInt(2);
        jogador.setBlind(moeda);
        inimigo.setBlind(1 - moeda);
        novaRodada();
    }

    private void novaRodada() {

        Timer timer = new Timer();
        System.out.println("Rodada " + rodada.getRound());
        etapaRodada = 0;

        fimRodada = false;

        jogador.limpaCartas();
        inimigo.limpaCartas();

        baralho = new Baralho();
        baralho.criaBaralho();
        baralho.embaralhaBaralho();

        mesa = new Mesa(baralho);
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        timer.schedule(new TimerTask() { public void run() { jogador.revelaCarta(0); }}, 500);
        timer.schedule(new TimerTask() { public void run() { jogador.revelaCarta(1); }}, 700);


        jogadorPronto = false;
        inimigoPronto = false;

        if (onNovaRodada != null) {
            onNovaRodada.run();
        }
    }

    public void registrarEscolhaJogador(int escolha) {
        // Se houver aposta no pote e jogador der check, trata como call
        if (escolha == 1 && pote.getQuantidade() > 0) {
            jogadaJogador = 1; // Call
            int valorCall = pote.getQuantidade();
            pote.adicionarApostaJogador(valorCall);
            jogador.getVida().setVida(jogador.getVida().getVida() - valorCall);
        } else {
            jogadaJogador = escolha;
        }
        jogadorPronto = true;
        registrarEscolhaInimigo();
    }

    public void registrarEscolhaJogador(int escolha, int valor) {
        if (escolha == 2) { // Raise
            pote.adicionarApostaJogador(valor);
            jogador.getVida().setVida(jogador.getVida().getVida() - valor);
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
        // Verifica fold primeiro
        if (jogadaJogador == 0) {
            System.out.println("Inimigo venceu (jogador foldou)!");
            finalizarRodada(PokerLogica.Resultado.INIMIGO_VENCE);
            SwingUtilities.invokeLater(this::novaRodada);
            return;
        } else if (jogadaInimigo == 0) {
            System.out.println("Jogador venceu (inimigo foldou)!");
            finalizarRodada(PokerLogica.Resultado.JOGADOR_VENCE);
            SwingUtilities.invokeLater(this::novaRodada);
            return;
        }

        // Se chegou até aqui, ninguém foldou
        if (fimRodada) {
            inimigo.revelaCarta(0);
            inimigo.revelaCarta(1);

            PokerLogica.Resultado resultado = PokerLogica.avaliarRodada(
                    Arrays.asList(jogador.getMao()),
                    Arrays.asList(inimigo.getMao()),
                    Arrays.asList(mesa.getCartas())
            );

            finalizarRodada(resultado);
            SwingUtilities.invokeLater(this::novaRodada);
        }
    }

    private void processarApostas() {
        int apostaInimigo = inimigo.calcularAposta(pote, mesa, etapaRodada);
        pote.adicionarApostaInimigo(apostaInimigo);

        atualizarInterfaceApostas();
    }

    private void atualizarInterfaceApostas() {
        SwingUtilities.invokeLater(() -> {

            vidaGUIJogador.mostrarAposta(pote.getApostaJogador() / 100);
            vidaGUIInimigo.mostrarAposta(pote.getApostaInimigo() / 100);

            poteGUI.adicionarPote(pote.getQuantidade());
        });
    }

    private void finalizarRodada(PokerLogica.Resultado resultado) {
        if (vidaGUIJogador == null || vidaGUIInimigo == null) {
            System.err.println("Erro: VidaGUIs não foram configuradas!");
            return;
        }

        switch (resultado) {
            case JOGADOR_VENCE:
                vidaGUIJogador.retornarVida(pote.getApostaJogador() / 100);
                vidaGUIInimigo.perderVida(pote.getApostaInimigo() / 100);
                pote.transferirParaVencedor(true);
                break;

            case INIMIGO_VENCE:
                vidaGUIInimigo.retornarVida(pote.getApostaInimigo() / 100);
                vidaGUIJogador.perderVida(pote.getApostaJogador() / 100);
                pote.transferirParaVencedor(false);
                break;

            case EMPATE:
                vidaGUIJogador.retornarVida(pote.getApostaJogador() / 100);
                vidaGUIInimigo.retornarVida(pote.getApostaInimigo() / 100);
                break;
        }

        // Verifica se o jogo acabou
        if (verificarFimDeJogo()) {
            finalizarJogo(jogador.getVidaAtual() > 0);
            return;
        }

        pote.resetPote();
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

    private void finalizarJogo(boolean jogadorVenceu) {

        System.out.println(jogadorVenceu ? "Você venceu o jogo!" : "Você perdeu o jogo!");

        // Encerra o jogo
        System.exit(0);
        //Só pra debug, vou mudar depois
    }


    public Round getRound(){ return rodada; }
    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Mesa getMesa() { return mesa; }
    public Pote getPote() { return pote; }
    public Vida getVida() {
        return this.vida;
    }

    public int getVidaAtual() {
        return this.vida.getVida();
    }
}
