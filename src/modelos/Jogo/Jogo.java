
package modelos.Jogo;

import java.util.*;
import modelos.Cartas.*;

public class Jogo {
    private int rodada = 1;
    private int etapaRodada = 0;

    private Baralho baralho;
    private Pote pote;
    private Jogador jogador;
    private Inimigo inimigo;
    private Mesa mesa;

    private boolean jogadorPronto = false;
    private boolean inimigoPronto = false;

    private int jogadaJogador;
    private int jogadaInimigo;

    private Runnable onNovaRodada;

    public Jogo() {
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
        System.out.println("Rodada " + rodada++);
        etapaRodada = 0;

        jogador.limpaCartas();
        inimigo.limpaCartas();

        baralho = new Baralho();
        baralho.criaBaralho();
        baralho.embaralhaBaralho();

        mesa = new Mesa(baralho);
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        jogador.revelaCarta(0);
        jogador.revelaCarta(1);

        jogadorPronto = false;
        inimigoPronto = false;

        if (onNovaRodada != null) {
            onNovaRodada.run();
        }
    }

    public void registrarEscolhaJogador(int escolha) {
        jogadaJogador = escolha;
        jogadorPronto = true;
        registrarEscolhaInimigo();
    }

    private void registrarEscolhaInimigo() {
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
            } else {
                timer.schedule(new TimerTask() {
                    public void run() {
                        novaRodada();
                    }
                }, 1000);
            }

            jogadorPronto = false;
            inimigoPronto = false;
        }
    }

    private void processarRodada() {
        if (jogadaJogador == 0) {
            System.out.println("Jogador desistiu.");
        } else if (jogadaInimigo == 0) {
            System.out.println("Inimigo desistiu.");
        } else {
            PokerLogica.Resultado resultado = new PokerLogica().avaliarRodada(
                    Arrays.asList(jogador.getMao()),
                    Arrays.asList(inimigo.getMao()),
                    Arrays.asList(mesa.getCartas())
            );

            switch (resultado) {
                case JOGADOR_VENCE -> System.out.println("Jogador venceu!");
                case INIMIGO_VENCE -> System.out.println("Inimigo venceu!");
                case EMPATE -> System.out.println("Empate!");
            }
        }
    }

    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Mesa getMesa() { return mesa; }
    public Pote getPote() { return pote; }
}
