package modelos.Cartas;

import java.util.List;

public class CoringaDetails {
    private List<Coringa> coringas;

    public List<Coringa> getCoringas() {
        return coringas;
    }

    public void setCoringas(List<Coringa> coringas) {
        this.coringas = coringas;
    }

    @Override
    public String toString() {
        return "CoringaDetails{" + "coringas=" + coringas + '}';
    }
}
