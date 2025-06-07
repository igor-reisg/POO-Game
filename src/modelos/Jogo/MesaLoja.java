package modelos.Jogo;

import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;
import modelos.Cartas.CoringaDetails;
import modelos.Cartas.CoringaReader;

import java.io.IOException;
import java.util.List;

public class MesaLoja {
    private List<Coringa> coringas;
    String caminhoCoringas = "/assets/data/coringas.json";

    public MesaLoja() {
        try {
            List<Coringa> coringasData = CoringaReader.CoringaRead(caminhoCoringas).getCoringas();
            this.coringas = coringasData;
        } catch(Exception e) {
            System.out.println("Coringas não carregados");
        }
    }

    public Coringa[] getCartas() {
        return coringas.toArray(new Coringa[0]);
    }
}

