package modelos.Jogo;

public class Pote {
    private int quantidade;

    public Pote(){
        quantidade = 0;
    }

    public void resetPote(){
        quantidade = 0;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }
    public int getQuantidade() {
        return quantidade;
    }

}
