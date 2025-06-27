package modelos.Jogo;

import java.util.ArrayList;
import java.util.List;

public class Pote {
    private int quantidade;
    private int apostaJogador;
    private int apostaInimigo;
    private final List<PoteListener> listeners = new ArrayList<PoteListener>();

    public Pote() {
        quantidade = 0;
        apostaJogador = 0;
        apostaInimigo = 0;
    }

    public void resetPote() {
        quantidade = 0;
        apostaJogador = 0;
        apostaInimigo = 0;
        for(PoteListener listener : listeners){
            listener.resetarPote();
        }
    }

    public void adicionarApostaJogador(int quantidade) {
        this.apostaJogador += quantidade;
        this.quantidade += quantidade;
        for(PoteListener listener : listeners) {
            listener.adicionarPote(this.quantidade);
        }
    }

    public void adicionarApostaInimigo(int quantidade) {
        this.apostaInimigo += quantidade;
        this.quantidade += quantidade;
        for(PoteListener listener : listeners) {
            listener.adicionarPote(this.quantidade);
        }
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getApostaJogador() {
        return apostaJogador;
    }

    public int getApostaInimigo() {
        return apostaInimigo;
    }
/*
    public void transferirParaVencedor(boolean jogadorVenceu) {
        if (jogadorVenceu) {
            quantidade += apostaInimigo;
        } else {
            quantidade += apostaJogador;
        }
    }
*/
    public void adicionarListener(PoteListener listener) {
        listeners.add(listener);
    }
}