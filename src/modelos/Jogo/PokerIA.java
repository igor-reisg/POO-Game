package modelos.Jogo;

import modelos.Cartas.Carta;
import java.util.*;

public class PokerIA {
    // Atributos da IA
    private int agressividade;  // 1-10 (1=passivo, 10=agressivo)
    private int cautela;        // 1-10 (1=arrojado, 10=cauteloso)
    private int inteligencia;   // 0-100 (nível de tomada de decisão inteligente)
    private int adaptabilidade; // 1-10 (capacidade de ajustar estratégia)
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
        this.adaptabilidade = calcularAdaptabilidade();
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

    private int calcularAdaptabilidade() {
        // Jogadores mais inteligentes são mais adaptáveis (5-10)
        // Jogadores menos inteligentes são mais previsíveis (1-5)
        int base = (int)(inteligencia / 15.0);
        return Math.max(1, Math.min(10, base + random.nextInt(3)));
    }

    public int decidirJogada(int vidaAtual, int apostaAtual, Mesa mesa, Carta[] mao, int etapaRodada) {
        List<Carta> cartasVisiveis = getCartasVisiveis(mesa);
        int forcaMao = avaliarForcaMao(mao, cartasVisiveis, etapaRodada);

        // A inteligência determina a chance de tomar a melhor decisão possível
        double chanceDecisaoOtima = 0.3 + (inteligencia / 100.0 * 0.7);

        // Fator de adaptação baseado no histórico
        double fatorAdaptacao = calcularFatorAdaptacao();

        // Probabilidade base ajustada pela adaptabilidade
        double probBase = calcularProbabilidadeBase(forcaMao, etapaRodada) * fatorAdaptacao;
        this.ultimaProbabilidade = probBase;

        // Decisão final
        int decisao;
        if (random.nextDouble() < chanceDecisaoOtima) {
            decisao = tomarDecisaoOtima(vidaAtual, apostaAtual, forcaMao, probBase);
        } else {
            decisao = tomarDecisaoEstilo(vidaAtual, apostaAtual, forcaMao);
        }

        // Atualizar histórico
        registrarJogada(decisao, forcaMao, apostaAtual > 0);
        return decisao;
    }

    private double calcularFatorAdaptacao() {
        // Jogadores adaptáveis ajustam mais seu estilo baseado no histórico
        double fator = 1.0;
        int totalJogadas = historicoJogadas.values().stream().mapToInt(Integer::intValue).sum();

        if (totalJogadas > 10) {
            double taxaFold = historicoJogadas.get("fold") / (double) totalJogadas;
            double taxaRaise = historicoJogadas.get("raise") / (double) totalJogadas;

            // Ajustar agressividade baseado no histórico
            if (taxaFold > 0.4) fator *= 1.1; // Está foldando muito, aumenta agressividade
            if (taxaRaise > 0.6) fator *= 0.9; // Está aumentando muito, reduz agressividade
        }

        return Math.max(0.7, Math.min(1.3, fator));
    }

    private double calcularProbabilidadeBase(int forcaMao, int etapaRodada) {
        double fatorAgressivo = agressividade / 10.0;
        double fatorCautela = cautela / 10.0;
        double aleatoriedade = (random.nextDouble() - 0.5) * 0.2;

        // Probabilidade base ajustada pela força da mão
        double probBase = (fatorAgressivo * 0.6) - (fatorCautela * 0.4) + aleatoriedade;
        probBase += (forcaMao - 50) / 100.0;

        // Ajuste por etapa da rodada
        switch(etapaRodada) {
            case 0: probBase -= 0.1; break; // Pré-flop
            case 1: probBase -= 0.05; break; // Flop
            case 3: probBase += 0.1; break;  // River
        }

        return Math.max(0, Math.min(1, probBase));
    }

    private int tomarDecisaoOtima(int vidaAtual, int apostaAtual, int forcaMao, double probBase) {
        // Decisão baseada em probabilidades e força da mão
        double razaoPote = apostaAtual > 0 ? (vidaAtual / (double)apostaAtual) : 1;

        if (vidaAtual < apostaAtual) {
            return 0; // Fold forçado
        }

        // Chance de bluff (inversamente proporcional à cautela)
        boolean isBluff = forcaMao < 40 && random.nextDouble() > (cautela / 10.0);

        if (apostaAtual == 0) { // Pode check ou raise
            if (probBase > 0.5 + (agressividade/20.0) || isBluff) {
                return 2; // Raise
            }
            return 1; // Check
        } else { // Precisa pagar para continuar
            if (probBase > 0.6 + (agressividade/20.0) || (isBluff && random.nextDouble() > 0.7)) {
                return 2; // Raise
            }
            if (probBase > 0.3 - (cautela/30.0)) {
                return 1; // Call
            }
            return 0; // Fold
        }
    }

    private int tomarDecisaoEstilo(int vidaAtual, int apostaAtual, int forcaMao) {
        // Decisão baseada no estilo de jogo
        double rand = random.nextDouble();
        double ajusteForca = forcaMao / 100.0;

        switch(estiloJogo) {
            case TAG: // Tight-Aggressive
                if (rand < 0.1 + ajusteForca * 0.3) return 0;
                if (rand < 0.8 + ajusteForca * 0.5) return 2;
                return 1;

            case LAG: // Loose-Aggressive
                if (rand < 0.2) return 0;
                if (rand < 0.9 - ajusteForca * 0.2) return 2;
                return 1;

            case NIT: // Nit (ultra conservador)
                if (rand < 0.6 - ajusteForca * 0.4) return 0;
                if (rand < 0.8) return 1;
                return 2;

            default: // Fish (aleatório)
                if (rand < 0.4) return 0;
                if (rand < 0.7) return 1;
                return 2;
        }
    }

    private void registrarJogada(int decisao, int forcaMao, boolean teveAposta) {
        String key;
        boolean isBluff = forcaMao < 40 && decisao == 2;

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
        // Base + ajuste por agressividade + aleatoriedade controlada por inteligência
        int base = 100 + ((agressividade - 5) * 20);
        int variacao = (int)((random.nextDouble() - 0.5) * 50 * (1 - (inteligencia/100.0)));
        int maxPossivel = vidaAtual - apostaAtual;

        // Jogadores inteligentes fazem aumentos mais precisos
        if (inteligencia > 70) {
            base = (int)(base * 0.8); // Aumentos menores mas mais frequentes
        }

        base = Math.min(base + variacao, maxPossivel);
        return Math.max(50, (base / 50) * 50); // Arredonda para múltiplos de 50
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
        return historicoJogadas.get("bluff") / (double) Math.max(1, historicoJogadas.get("raise"));
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

    // Métodos para debug
    public void debugIA() {
        System.out.println("=== Perfil IA ===");
        System.out.println("Tipo: " + getPerfil());
        System.out.println("Agressividade: " + agressividade + "/10");
        System.out.println("Cautela: " + cautela + "/10");
        System.out.println("Inteligência: " + inteligencia + "/100");
        System.out.println("Adaptabilidade: " + adaptabilidade + "/10");
        System.out.println("Estatísticas: " + getEstatisticas());
        System.out.println("Última Probabilidade: " + (ultimaProbabilidade * 100) + "%");
    }
}