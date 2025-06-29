package modelos.Loja;

import modelos.Cartas.Coringa;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sacola {
    private List<Coringa> coringasSacola;
    private int precoTotal;

    public Sacola() {
        this.coringasSacola = new ArrayList<>();
        this.precoTotal = 0;
    }

    // Adiciona um coringa e atualiza o preço total
    public void adicionarCoringaSacola(Coringa coringa) {
        coringasSacola.add(coringa);
        precoTotal += coringa.getPreco();
    }

    // Remove um coringa e atualiza o preço total
    public void removerCoringaSacola(Coringa coringa) {
        if (coringasSacola.remove(coringa)) {
            precoTotal -= coringa.getPreco();
        }
    }

    // Limpa a sacola e zera o preço
    public void limparSacola() {
        coringasSacola.clear();
        precoTotal = 0;
    }

    public List<Coringa> getCoringasSacola() {
        return coringasSacola;
    }

    public int getQtdCoringas() {
        return coringasSacola.size();
    }

    public int getPrecoTotal() {
        return precoTotal;
    }
}
