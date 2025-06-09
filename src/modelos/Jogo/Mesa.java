package modelos.Jogo;

import gui.Jogo.JogoGUI;
import modelos.Cartas.*;

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

    public void revelaCarta(){
        cartas[cartasViradas].virarCarta();
        cartasViradas++;
    }

    public void revelaCarta(int numeroDaCarta){


        cartas[numeroDaCarta].virarCarta();

        cartasViradas++;
    }

    public Carta[] getCartas(){
        return cartas;
    }


}
