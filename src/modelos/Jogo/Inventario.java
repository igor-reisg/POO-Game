package modelos.Jogo;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final int maxCoringas = 5;
    List<Coringa> coringasInventario;

    public Inventario() {
        this.coringasInventario = new ArrayList<>();
    }

    public boolean adicionarCoringa(Coringa coringa) {
        if (coringasInventario.size() < maxCoringas) {
            coringasInventario.add(coringa);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removerCoringa(Coringa coringa) {
        coringasInventario.remove(coringa);
        return true;
    }

    public List<Coringa> listarCoringas() {
        return coringasInventario;
    }

    public boolean inventarioCheio() {
        return coringasInventario.size() >= maxCoringas;
    }


}
