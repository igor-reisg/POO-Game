package modelos.Jogo;

import java.util.Random;
import modelos.Cartas.*;

public class Jogo {
    int level;
    int fase;
    int rodada;
    private int etapaRodada = 0;

    Baralho baralho;
    Pote pote;
    Jogador jogador;
    Inimigo inimigo;
    Mesa mesa;

    private boolean jogadorPronto;
    private boolean inimigoPronto;
    private int jogadaJogador;
    private int jogadaInimigo;

    public Jogo() {
        this.rodada = 1;
        this.fase = 1;
        this.level = 1;

        pote = new Pote();
        inimigo = new Inimigo();
        jogador = new Jogador("Naka");

        baralho = new Baralho();
        baralho.criaBaralho();
        baralho.embaralhaBaralho();

        mesa = new Mesa(baralho);
        mesa.recebeCarta(baralho);

        iniciarJogo();
    }

    public void iniciarJogo() {
        // Define quem é SB/BB aleatoriamente
        Random r = new Random();
        int moeda = r.nextInt(2);

        jogador.setBlind(moeda);
        inimigo.setBlind(1 - moeda);

        novaRodada();
    }

    private void novaRodada() {
        System.out.println("Rodada " + rodada++);
        etapaRodada = 0;

        // Deduz blinds
        if (jogador.getBlind() == 1) {
            jogador.setVida(jogador.getVida() - 200);
            pote.setQuantidade(200);
            inimigo.setVida(inimigo.getVida() - 100);
            pote.setQuantidade(100);
            System.out.println("Jogador é SB");
        } else {
            jogador.setVida(jogador.getVida() - 100);
            pote.setQuantidade(200);
            inimigo.setVida(inimigo.getVida() - 200);
            pote.setQuantidade(100);
            System.out.println("Jogador é BB");
        }

        // Reinicia mesa e baralho
        baralho = new Baralho();
        baralho.criaBaralho();
        baralho.embaralhaBaralho();

        mesa = new Mesa(baralho);
        mesa.recebeCarta(baralho);

        // Distribui cartas
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        jogadorPronto = false;
        inimigoPronto = false;
    }

    public void registrarEscolhaJogador(int escolha) {
        this.jogadaJogador = escolha;
        jogadorPronto = true;

        System.out.println("Jogador realizou jogada");
        registrarEscolhaInimigo(); // IA
    }

    public void registrarEscolhaInimigo() {
        this.jogadaInimigo = inimigo.getJogada();
        inimigoPronto = true;

        System.out.println("Inimigo realizou jogada");
        verificarSePodeAvancar();
    }

    public void verificarSePodeAvancar() {
        if (jogadorPronto && inimigoPronto) {
            processarRodada();
            jogadorPronto = false;
            inimigoPronto = false;

            etapaRodada++;

            switch (etapaRodada) {
                case 1:
                    mesa.revelaCarta(0);
                    mesa.revelaCarta(1);
                    mesa.revelaCarta(2);
                    System.out.println("Flop dado");
                    break;
                case 2:
                    mesa.revelaCarta(3);
                    System.out.println("Turn dado");
                    break;
                case 3:
                    mesa.revelaCarta(4);
                    System.out.println("River dado");
                    break;
                case 4:
                    finalizarJogo();
                    return;
            }

            System.out.println("Etapa rodada " + etapaRodada + " processada");
        }
    }

    public void processarRodada() {
        if (jogadaJogador == 0) {
            jogador.setVida(jogador.getVida() - pote.getQuantidade());
            System.out.println("Jogador perdeu a rodada");
        } else if (jogadaInimigo == 0) {
            inimigo.setVida(inimigo.getVida() - pote.getQuantidade());
            System.out.println("Inimigo perdeu a rodada");
        } else {
            System.out.println("Ambos continuam na rodada");
        }
    }

    public void finalizarJogo() {
        if (jogador.getVida() <= 0) {
            System.out.println("Você perdeu!");
        } else if (inimigo.getVida() <= 0) {
            System.out.println("Você venceu!");
        } else {
            System.out.println("Fim da rodada. Iniciando próxima...");
            novaRodada(); // Reinicia
        }
    }


    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Mesa getMesa() { return mesa; }
    public Pote getPote() { return pote; }
}
