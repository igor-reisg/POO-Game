package modelos.Jogo;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final int maxCoringas = 5;
    List<Coringa> coringasInventario;
    private int valorTotal;
    private int moedas;

    public Inventario() {
        this.coringasInventario = new ArrayList<>();
        this.valorTotal = 0;
        this.moedas = 20;

        for (int i = 0; i < coringasInventario.size(); i++) {
            valorTotal += coringasInventario.get(i).getPreco();
        }
    }

    public boolean adicionarCoringa(Coringa coringa) {
        if (coringasInventario.size() < maxCoringas) {
            coringasInventario.add(coringa);
            valorTotal += coringa.getPreco();
            return true;
        }
        else {
            return false;
        }
    }

    public String getMoedasInventarioString() {
        return moedas + "";
    }

    public int getMoedasInventario() {
        return moedas;
    }

    public boolean venderCoringa(Coringa coringa) {
        coringasInventario.remove(coringa);
        moedas += coringa.getPreco();
        valorTotal -= coringa.getPreco();
        return true;
    }

    public void usarMoedas(int valor) {
        if (moedas > 0) {
            moedas -= valor;
        }
    }

    public List<Coringa> listarCoringas() {
        return coringasInventario;
    }

    public boolean inventarioCheio() {
        return coringasInventario.size() >= maxCoringas;
    }

    public int getQtdCoringas() {
        return coringasInventario.size();
    }

    public int getMaxCoringas() {
        return maxCoringas;
    }
}
