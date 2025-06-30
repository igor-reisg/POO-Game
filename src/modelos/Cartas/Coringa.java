package modelos.Cartas;

public class Coringa {
    private final String imagemCaminho;
    private final int preco;
    private final String raridade;
    private final String nome;
    private final String descricao;
    private boolean verso = false;

    public Coringa() {
        this.imagemCaminho = null;
        this.preco = 0;
        this.raridade = null;
        this.nome = null;
        this.descricao = null;
    }

    public Coringa(String nome, String descricao, String raridade, int preco, String imagemCaminho) {
        this.preco = preco;
        this.raridade = raridade;
        this.nome = nome;
        this.descricao = descricao;
        this.imagemCaminho = imagemCaminho;
    }

    public String getNome() {
        return nome;
    }

    public int getPreco() {
        return preco;
    }

    public String getRaridade() {
        return raridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemCaminho() {
        return imagemCaminho;
    }

    public void virarCarta() {
        this.verso = !this.verso;
    }

}
