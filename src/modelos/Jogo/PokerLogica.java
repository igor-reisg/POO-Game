package modelos.Jogo;

import modelos.Cartas.Carta;

import java.util.*;
import java.util.stream.Collectors;

public class PokerLogica {

    public enum Resultado {
        JOGADOR_VENCE, INIMIGO_VENCE, EMPATE
    }

    public static Resultado avaliarRodada(List<Carta> cartasJogador, List<Carta> cartasInimigo, List<Carta> mesa) {
        List<Carta> maoJogador = new ArrayList<>(cartasJogador);
        maoJogador.addAll(mesa);

        List<Carta> maoInimigo = new ArrayList<>(cartasInimigo);
        maoInimigo.addAll(mesa);

        int valorJogador = avaliarMao(maoJogador);
        int valorInimigo = avaliarMao(maoInimigo);

        if (valorJogador > valorInimigo) {
            return Resultado.JOGADOR_VENCE;
        } else if (valorInimigo > valorJogador) {
            return Resultado.INIMIGO_VENCE;
        } else {
            return Resultado.EMPATE;
        }
    }

    public static int avaliarMao(List<Carta> cartas) {
        // Exemplo simplificado: verifica apenas se há uma trinca, par ou carta alta
        Map<Integer, Long> frequencia = cartas.stream()
                .collect(Collectors.groupingBy(c -> c.getValor().ordinal(), Collectors.counting()));

        boolean ehFlush = cartas.stream().map(c -> c.getNaipe().name()).distinct().count() == 1;

        List<Integer> valoresOrdenados = cartas.stream()
                .map(c -> c.getValor().ordinal())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        boolean ehStraight = ehSequencia(valoresOrdenados);

        if (ehFlush && ehStraight) return 800;
        if (frequencia.containsValue(4L)) return 700;
        if (frequencia.containsValue(3L) && frequencia.containsValue(2L)) return 600;
        if (ehFlush) return 500;
        if (ehStraight) return 400;
        if (frequencia.containsValue(3L)) return 300;
        if (frequencia.values().stream().filter(v -> v == 2L).count() == 2) return 200;
        if (frequencia.containsValue(2L)) return 100;

        return valoresOrdenados.get(0); // carta mais alta
    }

    private static boolean ehSequencia(List<Integer> valoresOrdenados) {
        Set<Integer> unicos = new HashSet<>(valoresOrdenados);
        List<Integer> valores = new ArrayList<>(unicos);
        Collections.sort(valores);

        for (int i = 0; i <= valores.size() - 5; i++) {
            boolean sequencia = true;
            for (int j = 0; j < 4; j++) {
                if (valores.get(i + j) + 1 != valores.get(i + j + 1)) {
                    sequencia = false;
                    break;
                }
            }
            if (sequencia) return true;
        }

        // Checa caso especial: A-2-3-4-5
        return valores.containsAll(List.of(0, 1, 2, 3, 12)); // Supondo Ás = 12
    }
}
