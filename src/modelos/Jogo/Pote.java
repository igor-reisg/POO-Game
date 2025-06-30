package modelos.Jogo;

import java.util.ArrayList;
import java.util.List;

public class Pote {
    private int quantidade;
    private List<Integer> historicoApostaJogador = new ArrayList<>();
    private List<Integer> historicoApostaInimigo = new ArrayList<>();
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
        historicoApostaInimigo.clear();
        historicoApostaJogador.clear();
        for(PoteListener listener : listeners){
            listener.resetarPote();
        }
    }

    public void adicionarApostaJogador(int quantidade) {
        historicoApostaJogador.add(quantidade);
        this.apostaJogador += quantidade;
        this.quantidade += quantidade;
        for(PoteListener listener : listeners) {
            listener.adicionarPote(this.quantidade);
        }
    }

    public void adicionarApostaInimigo(int quantidade) {
        historicoApostaInimigo.add(quantidade);
        this.apostaInimigo += quantidade;
        this.quantidade += quantidade;
        for(PoteListener listener : listeners) {
            listener.adicionarPote(this.quantidade);
        }
    }

    public int getUltimaApostaJogador(){
        return historicoApostaJogador.get(historicoApostaJogador.size()-1);
    }

    public int getUltimaApostaInimigo(){
        return historicoApostaInimigo.get(historicoApostaInimigo.size()-1);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getApostaJogador() {
        return apostaJogador;
    }
    public List<Integer> getHistoricoApostaJogador() {
        return historicoApostaJogador;
    }
    public List<Integer> getHistoricoApostaInimigo() {
        return historicoApostaInimigo;
    }

    public int getApostaInimigo() {
        return apostaInimigo;
    }
    public void adicionarListener(PoteListener listener) {
        listeners.add(listener);
    }
}