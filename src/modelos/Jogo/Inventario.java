package modelos.Jogo;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final int maxCoringas = 5;
    private List<Coringa> coringasInventario;
    private int valorTotal;
    private int moedas;

    public Inventario() {
        this.coringasInventario = new ArrayList<>();
        this.valorTotal = 0;
        this.moedas = 10000;
    }

    public void adicionarCoringas(List<Coringa> coringas) {
        coringasInventario.addAll(coringas);
    }

    public boolean venderCoringa(Coringa coringa) {
        if (coringasInventario.remove(coringa)) {
            moedas += coringa.getPreco();
            return true;
        }
        return false;
    }

    // Usa as moedas para compras
    public void usarMoedas(int valor) {
        if (moedas >= valor) {
            moedas -= valor;
        }
    }

    // Getters
    public List<Coringa> listarCoringas() {
        return coringasInventario;
    }

    public String getMoedasInventarioString() {
        return moedas + "";
    }

    public int getMoedasInventario() {
        return moedas;

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
