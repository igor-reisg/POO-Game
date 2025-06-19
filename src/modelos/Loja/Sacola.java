package modelos.Loja;

import modelos.Cartas.Coringa;

public class Sacola {
    private int qtdCoringas;

    public Sacola() {
        qtdCoringas = 0;
    }

    public boolean possivelComprar() {
        return true;
    }

    public void adicionarCoringaSacola(Coringa coringa) {

    }

    public void contagemCoringas() {
        qtdCoringas++;
    }

    public int getQtdCoringas() {
        return qtdCoringas;
    }
}
