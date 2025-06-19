package modelos.Jogo;

public class Pote {
    private int quantidade;

    public Pote(){
        quantidade = 0;
    }

    public void resetPote(){
        quantidade = 0;
    }

    public void adicionarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public void setQuantidadeAbsoluta(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

}
