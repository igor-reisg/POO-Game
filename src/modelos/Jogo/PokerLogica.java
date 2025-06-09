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

        System.out.print("JOGADOR TEM: ");
        MaoAvaliacao avaliacaoJogador = avaliarMao(maoJogador);
        System.out.print("INIMIGO TEM: ");
        MaoAvaliacao avaliacaoInimigo = avaliarMao(maoInimigo);

        int comparacao = avaliacaoJogador.compareTo(avaliacaoInimigo);
        if (comparacao > 0) {
            return Resultado.JOGADOR_VENCE;
        } else if (comparacao < 0) {
            return Resultado.INIMIGO_VENCE;
        } else return Resultado.EMPATE;
    }

    private static class MaoAvaliacao implements Comparable<MaoAvaliacao> {
        int forca; // tipo da mão: 800 = straight flush, 700 = quadra, etc.
        List<Integer> desempate; // valores para desempate: kicker, par alto, etc.

        public MaoAvaliacao(int forca, List<Integer> desempate) {
            this.forca = forca;
            this.desempate = desempate;
        }

        @Override
        public int compareTo(MaoAvaliacao o) {
            if (this.forca != o.forca) return Integer.compare(this.forca, o.forca);

            for (int i = 0; i < Math.min(this.desempate.size(), o.desempate.size()); i++) {
                int comp = Integer.compare(this.desempate.get(i), o.desempate.get(i));
                if (comp != 0) return comp;
            }
            return 0;
        }
    }

    public static MaoAvaliacao avaliarMao(List<Carta> cartas) {
        Map<Integer, Long> freq = cartas.stream()
                .collect(Collectors.groupingBy(c -> c.getValor().ordinal(), Collectors.counting()));

        boolean ehFlush = cartas.stream().map(c -> c.getNaipe().name()).distinct().count() == 1;

        List<Integer> valoresOrdenados = cartas.stream()
                .map(c -> c.getValor().ordinal())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        boolean ehStraight = ehSequencia(valoresOrdenados);

        List<Integer> desempate = new ArrayList<>();

        if (ehFlush && ehStraight) {
            desempate.addAll(valoresOrdenados);
            System.out.println("STRAIGH FLUSH");
            return new MaoAvaliacao(800, desempate);
        }

        if (freq.containsValue(4L)) {
            int quadra = getPorFrequencia(freq, 4L).get(0);
            desempate.add(quadra);
            desempate.addAll(getRestantes(freq, List.of(quadra)));
            System.out.println("QUADRA");
            return new MaoAvaliacao(700, desempate);
        }

        if (freq.containsValue(3L) && freq.containsValue(2L)) {
            int trinca = getPorFrequencia(freq, 3L).get(0);
            int par = getPorFrequencia(freq, 2L).get(0);
            desempate.add(trinca);
            desempate.add(par);
            System.out.println("FULL HOUSE");
            return new MaoAvaliacao(600, desempate);
        }

        if (ehFlush) {
            desempate.addAll(valoresOrdenados);
            System.out.println("FLUSH");
            return new MaoAvaliacao(500, desempate);
        }

        if (ehStraight) {
            desempate.addAll(valoresOrdenados);
            System.out.println("SEQUENCIA");
            return new MaoAvaliacao(400, desempate);
        }

        if (freq.containsValue(3L)) {
            int trinca = getPorFrequencia(freq, 3L).get(0);
            desempate.add(trinca);
            desempate.addAll(getRestantes(freq, List.of(trinca)));
            System.out.println("TRINCA");
            return new MaoAvaliacao(300, desempate);
        }

        if (freq.values().stream().filter(v -> v == 2L).count() == 2) {
            List<Integer> pares = getPorFrequencia(freq, 2L);
            pares.sort(Comparator.reverseOrder());
            desempate.addAll(pares);
            desempate.addAll(getRestantes(freq, pares));
            System.out.println("DOIS PARES");
            return new MaoAvaliacao(200, desempate);
        }

        if (freq.containsValue(2L)) {
            int par = getPorFrequencia(freq, 2L).get(0);
            desempate.add(par);
            desempate.addAll(getRestantes(freq, List.of(par)));
            System.out.println("PAR");
            return new MaoAvaliacao(100, desempate);
        }

        desempate.addAll(valoresOrdenados);
        System.out.println("CARTA ALTA");
        return new MaoAvaliacao(0, desempate);
    }

    private static List<Integer> getPorFrequencia(Map<Integer, Long> freq, long alvo) {
        return freq.entrySet().stream()
                .filter(e -> e.getValue() == alvo)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<Integer> getRestantes(Map<Integer, Long> freq, List<Integer> usados) {
        return freq.entrySet().stream()
                .filter(e -> !usados.contains(e.getKey()))
                .sorted((a, b) -> Long.compare(b.getKey(), a.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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

        // A-2-3-4-5: Ás como 12
        return valores.containsAll(List.of(0, 1, 2, 3, 12));
    }
}
