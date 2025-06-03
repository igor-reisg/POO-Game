package modelos.Cartas;

import modelos.Cartas.*;

import java.util.*;

public class Baralho {
    private final Carta[] cartas;
    private int cartasDisponiveis;
    private int cartasUsadas;

    public Baralho() {
        cartas = new Carta[52];
    }

    public void criaBaralho() {
        int cont = 0;
        for (Carta.Naipe naipe : Carta.Naipe.values()) {
            for (Carta.Valor valor : Carta.Valor.values()) {
                cartas[cont++] = new Carta(naipe, valor);
            }
        }
        cartasDisponiveis = 52;
        cartasUsadas = 0;
    }

    public void embaralhaBaralho() {
        Random random = new Random();

        for (int i = 0; i < cartas.length; i++) {
            int posicaoAleatoria = random.nextInt(cartas.length - i) + i;
            Carta cartaAleatoria = cartas[posicaoAleatoria];
            cartas[posicaoAleatoria] = cartas[i];
            cartas[i] = cartaAleatoria;
        }
    }

    public Carta pegaCarta() {
        if (cartasDisponiveis > 0) {
            Carta carta = cartas[cartasUsadas];
            cartasUsadas++;
            cartasDisponiveis--;
            return carta;
        }
        else {
            System.out.println("Baralho vazio!");
            return null;
        }
    }

    public void reiniciaBaralho() {
        criaBaralho();
        embaralhaBaralho();
    }

    public List<List<Carta>> distribuirCartasJogadores() {
        List<List<Carta>> maosJogadores = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            List<Carta> mao = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                if (cartasDisponiveis > 0) {
                    mao.add(pegaCarta());
                }
                maosJogadores.add(mao);
            }
        }
        return maosJogadores;
    }

    public List<Carta> distribuirCartasMesa() {
        List<Carta> cartasMesa = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (cartasDisponiveis > 0) {
                cartasMesa.add(pegaCarta());
            }
        }
        return cartasMesa;
    }
}
