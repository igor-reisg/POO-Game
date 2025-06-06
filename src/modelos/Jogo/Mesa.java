package modelos.Jogo;

import modelos.Cartas.*;

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

    }

    public void recebeCarta(Baralho baralho){
        cartas[0] = baralho.pegaCarta();
        cartas[1] = baralho.pegaCarta();
        cartas[2] = baralho.pegaCarta();
        cartas[3] = baralho.pegaCarta();
        cartas[4] = baralho.pegaCarta();

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
