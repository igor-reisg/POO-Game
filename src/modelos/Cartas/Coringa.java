package modelos.Cartas;

public class Coringa {
    private final String imagemCaminho;
    private final int preco;
    private final String raridade;
    private final String nome;
    private final String descricao;
    private final int xp;
    private final int level;
    private boolean verso = false;

    public Coringa() {
        this.imagemCaminho = null;
        this.preco = 0;
        this.raridade = null;
        this.nome = null;
        this.descricao = null;
        this.xp = 0;
        this.level = 0;
    }

    public Coringa(String nome, String descricao, String raridade, int preco, int xp, int level, String imagemCaminho) {
        this.preco = preco;
        this.raridade = raridade;
        this.xp = xp;
        this.level = level;
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

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public String getImagemCaminho() {
        return imagemCaminho;
    }

    public void virarCarta() {
        this.verso = !this.verso;
    }

}
