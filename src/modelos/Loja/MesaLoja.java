package modelos.Loja;

import modelos.Cartas.Coringa;
import modelos.Cartas.CoringaReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MesaLoja {
    private List<Coringa> coringas;
    private List<Coringa> coringasMesa;
    private final String caminhoCoringas = "/assets/data/coringas.json";

    public MesaLoja() {
        try {
            this.coringas = CoringaReader.CoringaRead(caminhoCoringas).getCoringas();
        } catch (Exception e) {
            System.out.println("Coringas não carregados: " + e.getMessage());
            this.coringas = new ArrayList<>();
        }
        gerarNovosCoringas();
    }

    public void gerarNovosCoringas() {
        if (coringas.size() >= 6) {
            Collections.shuffle(coringas);
            coringasMesa = new ArrayList<>(coringas.subList(0, 6));
        } else {
            coringasMesa = new ArrayList<>();
        }
    }

    public Coringa[] getCartas() {
        if (coringasMesa == null || coringasMesa.isEmpty()) {
            gerarNovosCoringas(); // Garante que sempre haverá cartas
        }
        return coringasMesa.toArray(new Coringa[0]);
    }
}