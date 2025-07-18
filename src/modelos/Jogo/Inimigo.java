package modelos.Jogo;

import modelos.Cartas.Baralho;
import modelos.Cartas.Carta;

public class Inimigo {
    public PerfilInimigo perfil;
    private Vida vida;
    private int jogada;
    private Carta[] mao;
    private int blind;
    private PokerIA pokerIA;

    public Inimigo(PerfilInimigo perfil) {
        this.perfil = perfil;
        this.vida = new Vida(perfil.getVidaInicial());
        this.mao = new Carta[2];
        this.pokerIA = new PokerIA(
                perfil.getAgressividadeIA(),
                perfil.getCautelaIA(),
                perfil.getInteligenciaIA()
        );
    }

    public void decidirJogada(Pote pote, Mesa mesa, int etapaRodada) {
        System.out.println("\n[IA] Decidindo jogada...");
        System.out.println("Vida: " + vida.getVida() + " | Pote: " + pote.getQuantidade());

        this.jogada = pokerIA.decidirJogada(
                vida.getVida(),
                pote.getQuantidade(),
                mesa,
                this.mao,
                etapaRodada
        );

        int valorAumento = 0;
        int valorCall = 0;
        if (this.jogada == 2) { // Raise
            valorAumento = pokerIA.calcularValorAumento(vida.getVida(), pote.getQuantidade());
            valorAumento = Math.min(vida.getVida(), valorAumento);

            vida.selecionarVida(pote.getUltimaApostaJogador() + valorAumento);
            vida.setVida(vida.getVida() - valorAumento);
            pote.adicionarApostaInimigo(valorAumento);
            System.out.println("Inimigo deu raise: " + valorAumento);
        }
        else if (this.jogada == 1) { // Call ou check
            valorCall = Math.max(0, pote.getUltimaApostaJogador() - pote.getUltimaApostaInimigo());
            valorCall = Math.min(valorCall, vida.getVida());
            vida.selecionarVida(pote.getApostaInimigo() + valorCall);
            vida.setVida(vida.getVida() - valorCall);
            pote.adicionarApostaInimigo(valorCall);
            if(valorCall == 0){
                System.out.println("Inimigo deu check:");
            } else{
                System.out.println("Inimigo deu call: " + valorCall);
            }
            pote.adicionarApostaInimigo(0);
        }
        System.out.println("[IA] Decisão: " +
                (jogada == 0 ? "FOLD" : jogada == 1 ? "CALL " + valorCall : "RAISE " + valorAumento));
    }

    public int calcularAposta(Pote pote, Mesa mesa, int etapaRodada) {

        int valor = pokerIA.calcularValorAumento(vida.getVida(), pote.getQuantidade());
        return Math.min(valor, vida.getVida());
    }


    public void escolhaDaJogada(int escolha) {
        this.jogada = escolha;
    }

    public void limpaCartas() {
        this.mao = new Carta[2];
    }

    public void revelaCarta(int numeroDaCarta) {
        if (mao != null && numeroDaCarta >= 0 && numeroDaCarta < mao.length && mao[numeroDaCarta] != null) {
            mao[numeroDaCarta].virarCarta();
        }
    }

    public void recebeCarta(Baralho baralho) {
        mao = new Carta[2];
        mao[0] = baralho.pegaCarta();
        mao[1] = baralho.pegaCarta();
    }


    public void setVida(int vida) {
        this.vida.setVida(vida);
        if (this instanceof Boss) {
            ((Boss) this).atualizarEstadoHP();
        }
    }

    public Carta[] getMao() {
        return mao;
    }

    public int getBlind() {
        return blind;
    }

    public void setBlind(int blind) {
        this.blind = blind;
    }

    public int getJogada() {
        return jogada;
    }

    public void setJogada(int jogada) {
        this.jogada = jogada;
    }

    public PokerIA getPokerIA() {
        return pokerIA;
    }

    public void setPokerIA(PokerIA pokerIA) {
        this.pokerIA = pokerIA;
    }

    public Vida getVida() {
        return this.vida;
    }

    public int getVidaAtual() {
        return this.vida.getVida();
    }

    public PerfilInimigo getPerfil() {
        return perfil;
    }
}