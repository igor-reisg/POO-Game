package modelos.Jogo;

import modelos.Cartas.Carta;
import java.util.*;

public class PokerIA {
    // Atributos da IA
    private int agressividade;  // 1-10 (1=passivo, 10=agressivo)
    private int cautela;        // 1-10 (1=arrojado, 10=cauteloso)
    private int inteligencia;   // 0-100 (nível de tomada de decisão inteligente)
    private Random random;
    private Map<String, Integer> historicoJogadas;
    private int estiloJogo;     // 0=TAG, 1=LAG, 2=Nit, 3=Fish
    private double ultimaProbabilidade;

    // Constantes
    private static final int TAG = 0;
    private static final int LAG = 1;
    private static final int NIT = 2;
    private static final int FISH = 3;

    public PokerIA(int agressividade, int cautela, int inteligencia) {
        this.agressividade = Math.max(1, Math.min(10, agressividade));
        this.cautela = Math.max(1, Math.min(10, cautela));
        this.inteligencia = Math.max(0, Math.min(100, inteligencia));
        this.random = new Random();
        this.historicoJogadas = new HashMap<>();
        this.estiloJogo = determinarEstiloJogo();
        inicializarHistorico();
    }

    private void inicializarHistorico() {
        historicoJogadas.put("fold", 0);
        historicoJogadas.put("check", 0);
        historicoJogadas.put("call", 0);
        historicoJogadas.put("raise", 0);
        historicoJogadas.put("bluff", 0);
    }

    private int determinarEstiloJogo() {
        double ratio = agressividade / (double) cautela;

        if (ratio > 1.5 && agressividade > 7) return LAG;
        if (ratio > 1.2 && agressividade > 6) return TAG;
        if (ratio < 0.7 && cautela > 7) return NIT;
        return FISH;
    }

    public int decidirJogada(int vidaAtual, int apostaAtual, Mesa mesa, Carta[] mao, int etapaRodada) {
        List<Carta> cartasVisiveis = getCartasVisiveis(mesa);
        int forcaMao = avaliarForcaMao(mao, cartasVisiveis, etapaRodada);

        // Se não há aposta pendente, só pode check ou raise
        if (apostaAtual == 0) {
            return decidirAcaoSemAposta(forcaMao);
        }
        // Se há aposta pendente (jogador apostou primeiro), decide entre fold, call ou raise
        else {
            return decidirAcaoComAposta(vidaAtual, apostaAtual, forcaMao);
        }
    }

    private int decidirAcaoSemAposta(int forcaMao) {
        // Nunca foldar quando é a primeira a jogar
        double chanceRaise = calcularChanceRaise(forcaMao);

        if (random.nextDouble() < chanceRaise) {
            registrarJogada(2, forcaMao, false);
            return 2; // Raise
        }
        registrarJogada(1, forcaMao, false);
        return 1; // Check
    }

    private int decidirAcaoComAposta(int vidaAtual, int apostaAtual, int forcaMao) {
        // Decisão quando há aposta para call
        double chanceCall = calcularChanceCall(forcaMao, apostaAtual, vidaAtual);
        double chanceRaise = calcularChanceRaiseComAposta(forcaMao);

        double roll = random.nextDouble();

        if (roll < chanceRaise) {
            registrarJogada(2, forcaMao, true);
            return 2; // Raise
        } else if (roll < chanceCall + chanceRaise) {
            registrarJogada(1, forcaMao, true);
            return 1; // Call
        }
        registrarJogada(0, forcaMao, true);
        return 0; // Fold
    }

    private double calcularChanceRaise(int forcaMao) {
        double base = forcaMao / 100.0;
        double fatorAgressivo = agressividade / 12.0; // Ajustado para ser mais balanceado

        // Chance base + fator da mão + fator agressividade
        return Math.min(0.4 + (base * 0.5) + fatorAgressivo, 0.85);
    }

    private double calcularChanceCall(int forcaMao, int apostaAtual, int vidaAtual) {
        double base = forcaMao / 100.0;
        double razaoPote = vidaAtual > 0 ? apostaAtual / (double)vidaAtual : 0;
        double fatorCautela = cautela / 20.0;

        // Chance mínima de call aumenta com força da mão e cautela
        // Diminui com a razão pote/vida
        return Math.max(0.15, base * (1 - razaoPote * 0.5) + fatorCautela);
    }

    private double calcularChanceRaiseComAposta(int forcaMao) {
        double base = forcaMao / 100.0;
        double fatorAgressivo = agressividade / 25.0;
        return Math.min(0.2 + (base * 0.5) + fatorAgressivo, 0.6);
    }

    private void registrarJogada(int decisao, int forcaMao, boolean teveAposta) {
        String key;
        boolean isBluff = forcaMao < (50 - (agressividade * 2) + (inteligencia / 5))
                && decisao == 2
                && random.nextDouble() < (agressividade / 15.0);

        switch(decisao) {
            case 0: key = "fold"; break;
            case 1: key = teveAposta ? "call" : "check"; break;
            case 2:
                key = "raise";
                if (isBluff) historicoJogadas.put("bluff", historicoJogadas.get("bluff") + 1);
                break;
            default: key = "fold";
        }

        historicoJogadas.put(key, historicoJogadas.get(key) + 1);
    }

    public int calcularValorAumento(int vidaAtual, int apostaAtual) {
        // Garantir que não temos valores negativos
        vidaAtual = Math.max(0, vidaAtual);
        apostaAtual = Math.max(0, apostaAtual);

        // Valor base ajustado pela agressividade (100-300)
        int base = 100 + (agressividade * 20);

        // Aleatoriedade controlada pela inteligência
        int variacao = (int)(random.nextDouble() * 100 * (1 - (inteligencia/100.0)));

        // Limite máximo que pode apostar
        int maxPossivel = vidaAtual;

        // Cálculo do valor com limites
        int valorAposta = Math.min(base + variacao, maxPossivel);

        // Garantir múltiplo de 100 e mínimo de 100
        valorAposta = Math.max(100, (valorAposta / 100) * 100);

        System.out.println("[DEBUG IA] Calculo aposta: " +
                "base=" + base +
                ", var=" + variacao +
                ", max=" + maxPossivel +
                ", final=" + valorAposta);

        return valorAposta;
    }

    private List<Carta> getCartasVisiveis(Mesa mesa) {
        List<Carta> visiveis = new ArrayList<>();
        Carta[] todasCartas = mesa.getCartas();

        for (int i = 0; i < mesa.cartasViradas; i++) {
            if (todasCartas[i] != null && todasCartas[i].estaVirada()) {
                visiveis.add(todasCartas[i]);
            }
        }

        return visiveis;
    }

    private int avaliarForcaMao(Carta[] mao, List<Carta> cartasVisiveis, int etapa) {
        if (cartasVisiveis.isEmpty()) {
            return avaliarForcaPreFlop(mao);
        }

        List<Carta> maoCompleta = new ArrayList<>(Arrays.asList(mao));
        maoCompleta.addAll(cartasVisiveis);

        PokerLogica.MaoAvaliacao avaliacao = PokerLogica.avaliarMao(maoCompleta);

        // Converter avaliação para escala 0-100 com variação controlada pela inteligência
        int variacao = (int)((random.nextDouble() - 0.5) * 20 * (1 - (inteligencia/100.0)));

        if (avaliacao.forca >= 800) return 95 + variacao;
        if (avaliacao.forca >= 700) return 85 + variacao;
        if (avaliacao.forca >= 600) return 75 + variacao;
        if (avaliacao.forca >= 500) return 65 + variacao;
        if (avaliacao.forca >= 400) return 55 + variacao;
        if (avaliacao.forca >= 300) return 45 + variacao;
        if (avaliacao.forca >= 200) return 35 + variacao;
        if (avaliacao.forca >= 100) return 25 + variacao;
        return 10 + variacao;
    }

    private int avaliarForcaPreFlop(Carta[] mao) {
        int valor1 = mao[0].getValor().ordinal();
        int valor2 = mao[1].getValor().ordinal();
        boolean mesmoNaipe = mao[0].getNaipe() == mao[1].getNaipe();

        // Pares
        if (valor1 == valor2) {
            return 70 + valor1; // Pares altos valem mais
        }

        int max = Math.max(valor1, valor2);
        int min = Math.min(valor1, valor2);
        int diff = max - min;

        // Mãos premium
        if (max >= 11 && min >= 9) { // A, K, Q, J
            return mesmoNaipe ? 65 : 60;
        }

        // Conectores do mesmo naipe
        if (diff == 1 && mesmoNaipe) {
            return 55 + max;
        }

        // Conectores
        if (diff <= 2) {
            return 45 + max;
        }

        // Mesmo naipe
        if (mesmoNaipe) {
            return 40 + max;
        }

        // Cartas altas
        if (max >= 10) { // 10, J, Q, K, A
            return 30 + max;
        }

        // Mão fraca
        return 20 + max;
    }

    // Métodos de acesso e informações
    public String getPerfil() {
        String[] perfis = {"TAG (Tight-Aggressive)", "LAG (Loose-Aggressive)",
                "Nit (Ultra-Conservador)", "Fish (Jogador Fraco)"};
        return perfis[estiloJogo];
    }

    public int getNivelInteligencia() {
        return inteligencia;
    }

    public double getTaxaBluff() {
        int raises = historicoJogadas.getOrDefault("raise", 1);
        int bluffs = historicoJogadas.getOrDefault("bluff", 0);
        return bluffs / (double) raises;
    }

    public String getEstatisticas() {
        int total = historicoJogadas.values().stream().mapToInt(Integer::intValue).sum();
        if (total == 0) return "Nenhuma jogada registrada";

        return String.format(
                "Fold: %.1f%%, Check: %.1f%%, Call: %.1f%%, Raise: %.1f%% (Bluff: %.1f%%)",
                historicoJogadas.get("fold") * 100.0 / total,
                historicoJogadas.get("check") * 100.0 / total,
                historicoJogadas.get("call") * 100.0 / total,
                historicoJogadas.get("raise") * 100.0 / total,
                getTaxaBluff() * 100
        );
    }

    public double getUltimaProbabilidade() {
        return ultimaProbabilidade;
    }


}