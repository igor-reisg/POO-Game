package modelos.Jogo;

import modelos.Cartas.Coringa;

public class Boss extends Inimigo {
    private Coringa coringa;
    private int estadoHP; // 0 = vida cheia, 1 = 2/3, 2 = 1/3

    public Boss(PerfilInimigo perfil, Coringa coringa) {
        super(perfil);
        this.coringa = coringa;
        this.estadoHP = 0;
    }

    public Coringa getCoringa() {
        return coringa;
    }

    public void atualizarEstadoHP() {
        double porcentagemVida = (double)getVidaAtual() / perfil.getVidaInicial();

        if (porcentagemVida <= 1.0/3) {
            estadoHP = 2;
        } else if (porcentagemVida <= 2.0/3) {
            estadoHP = 1;
        } else {
            estadoHP = 0;
        }
    }

    public int getEstadoHP() {
        return estadoHP;
    }

}