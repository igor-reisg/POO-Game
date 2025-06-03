package modelos.Cartas;

import gui.Jogo.CoringasGUI;

import java.util.*;

public class CoringaDetails {
    private List<CoringasGUI> coringas;

    public List<CoringasGUI> getCoringas() {
        return coringas;
    }

    public void setCoringas(List<CoringasGUI> coringas) {
        this.coringas = coringas;
    }

    @Override
    public String toString() {
        return "Coringa: " + coringas;
    }
}
