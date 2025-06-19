package modelos.Loja;

import modelos.Cartas.Coringa;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sacola {
    private int qtdCoringas;
    List<Coringa> coringasSacola;
    private int precoTotal;

    public Sacola() {
        qtdCoringas = 0;
        precoTotal = 0;
        coringasSacola = new ArrayList<>();

        for (Coringa coringa : coringasSacola) {
            precoTotal += coringa.getPreco();
        }
    }

    public boolean possivelComprar() {
        return true;
    }

    public void adicionarCoringaSacola(Coringa coringa) {
        coringasSacola.add(coringa);
    }

    public void contagemCoringas() {
        qtdCoringas++;
    }

    public int getQtdCoringas() {
        return qtdCoringas;
    }

    public int getPrecoTotal() {
        return precoTotal;
    }
}
