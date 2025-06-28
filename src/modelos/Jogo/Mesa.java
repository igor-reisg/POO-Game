package modelos.Jogo;

import gui.Jogo.JogoGUI;
import modelos.Cartas.*;

import java.util.Arrays;
import java.util.TimerTask;

public class Mesa {
    private final Carta[] cartas;
    private Baralho baralho;
    int cartasViradas;


    public Mesa(Baralho baralho){
        this.cartasViradas = 0;
        cartasViradas = 0;
        this.baralho = baralho;
        cartas = new Carta[5];

        cartas[0] = baralho.pegaCarta();
        cartas[1] = baralho.pegaCarta();
        cartas[2] = baralho.pegaCarta();
        cartas[3] = baralho.pegaCarta();
        cartas[4] = baralho.pegaCarta();

//        timer.schedule(new TimerTask() { public void run() { cartas[0] = baralho.pegaCarta(); }}, 300);
//        timer.schedule(new TimerTask() { public void run() { cartas[1] = baralho.pegaCarta(); }}, 400);
//        timer.schedule(new TimerTask() { public void run() { cartas[2] = baralho.pegaCarta(); }}, 500);
//        timer.schedule(new TimerTask() { public void run() { cartas[3] = baralho.pegaCarta(); }}, 600);
//        timer.schedule(new TimerTask() { public void run() { cartas[4] = baralho.pegaCarta(); }}, 700);

    }
    public void revelaCarta(int numeroDaCarta) {
        if(numeroDaCarta >= 0 && numeroDaCarta < cartas.length && cartas[numeroDaCarta] != null) {
            cartas[numeroDaCarta].virarCarta();
            cartasViradas++;
            System.out.println("[DEBUG] Carta " + numeroDaCarta + " revelada: " +
                    cartas[numeroDaCarta].getValor() + " de " + cartas[numeroDaCarta].getNaipe());
        } else {
            System.err.println("[ERRO] Tentativa de revelar carta inválida: " + numeroDaCarta);
        }
    }

    public void resetCartas() {
        // Desvira as cartas anteriores (se necessário)
        for (Carta carta : cartas) {
            if (carta != null && carta.estaVirada()) {
                carta.virarCarta(); // Volta para o verso
            }
        }

        // Redistribui novas cartas
        cartas[0] = baralho.pegaCarta();
        cartas[1] = baralho.pegaCarta();
        cartas[2] = baralho.pegaCarta();
        cartas[3] = baralho.pegaCarta();
        cartas[4] = baralho.pegaCarta();

        cartasViradas = 0;
    }

    public Carta[] getCartas(){
        return cartas;
    }


}
