package modelos.Jogo;

public class Pote {
    private int quantidade;
    private int apostaJogador;
    private int apostaInimigo;

    public Pote() {
        quantidade = 0;
        apostaJogador = 0;
        apostaInimigo = 0;
    }

    public void resetPote() {
        quantidade = 0;
        apostaJogador = 0;
        apostaInimigo = 0;
    }

    public void adicionarApostaJogador(int quantidade) {
        this.apostaJogador += quantidade;
        this.quantidade += quantidade;
    }

    public void adicionarApostaInimigo(int quantidade) {
        this.apostaInimigo += quantidade;
        this.quantidade += quantidade;
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

    public void transferirParaVencedor(boolean jogadorVenceu) {
        if (jogadorVenceu) {
            quantidade += apostaInimigo;
        } else {
            quantidade += apostaJogador;
        }
    }
}