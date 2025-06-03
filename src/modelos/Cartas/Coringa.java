package modelos.Cartas;

public class Coringa {
    private final static String imagemCaminho = "/assets/images/cartas/coringas/";
    private final double preco;
    private final String efeito;
    private final String nome;
    private final String descricao;
    private final int xp;
    private final int level;
    private boolean verso = false;

    public Coringa(String nome, String descricao, String efeito, int preco, int xp, int nivel){
        this.preco = preco;
        this.efeito = efeito;
        this.xp = xp;
        this.level = nivel;
        this.nome = nome;
        this.descricao = descricao;
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

    public void virarCarta() {
        this.verso = !this.verso;

    }

    public String getImagemCaminho() {
        if(verso) {
            return imagemCaminho + "versos/" + "cartafundo.png";
        }
        else
            return imagemCaminho + nome + ".png";
    }
}
