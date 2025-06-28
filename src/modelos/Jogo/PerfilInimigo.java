package modelos.Jogo;

public class PerfilInimigo {
    private String nome;
    private String imagemPath;
    private int agressividadeIA;
    private int cautelaIA;
    private int inteligenciaIA;
    private int vidaInicial;
    private boolean isBoss;
    private String[] imagensBoss;

    public PerfilInimigo(String nome, String imagemPath, int agressividade, int cautela, int inteligencia, int vida, boolean isBoss, String[] imagensBoss) {
        this.nome = nome;
        this.imagemPath = imagemPath;
        this.agressividadeIA = agressividade;
        this.cautelaIA = cautela;
        this.inteligenciaIA = inteligencia;
        this.vidaInicial = vida;
        this.isBoss = isBoss;
        this.imagensBoss = imagensBoss;
    }

    public String getNome() { return nome; }
    public String getImagemPath() { return imagemPath; }
    public int getAgressividadeIA() { return agressividadeIA; }
    public int getCautelaIA() { return cautelaIA; }
    public int getInteligenciaIA() { return inteligenciaIA; }
    public int getVidaInicial() { return vidaInicial; }
    public boolean isBoss() { return isBoss; }
    public String[] getImagensBoss() { return imagensBoss; }
}