package modelos.Cartas;

public class Coringa {
    private final String imagemCaminho;
    private final double preco;
    private final String efeito;
    private final String nome;
    private final String descricao;
    private final int xp;
    private final int level;
    private boolean verso = false;

    public Coringa() {
        this.imagemCaminho = null;
        this.preco = 0;
        this.efeito = null;
        this.nome = null;
        this.descricao = null;
        this.xp = 0;
        this.level = 0;
    }

    public Coringa(String nome, String descricao, String efeito, double preco, int xp, int level, String imagemCaminho) {
        this.preco = preco;
        this.efeito = efeito;
        this.xp = xp;
        this.level = level;
        this.nome = nome;
        this.descricao = descricao;
        this.imagemCaminho = imagemCaminho;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getEfeito() {
        return efeito;
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
