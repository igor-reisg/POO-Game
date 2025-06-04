package modelos.Jogo;

import modelos.Cartas.Baralho;
import modelos.Cartas.Carta;

public class Enemy {
    int vida;
    String nome;
    Carta[] mao;
    int dinheiro;
    int blind;

    public Enemy(){

    }

    public void check(){

    }

    public void fold(){

    }



    public void recebeCarta(Baralho baralho){
        mao = new Carta[2];
        mao[0] = baralho.pegaCarta();
        mao[1] = baralho.pegaCarta();
    }

    public int getVida(){
        return vida;
    }
    public Carta[] getMao(){
        return mao;
    }
    public int getDinheiro(){
        return dinheiro;
    }
    public int getBlind(){
        return blind;
    }

}
