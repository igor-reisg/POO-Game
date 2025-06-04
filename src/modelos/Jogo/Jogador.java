package modelos.Jogo;

import modelos.Cartas.*;

public class Player {
    private int vida;
    private String nome;
    private Carta[] mao;
    private int dinheiro;
    private int blind;

    public Player(String nome){
        vida = 150;
        this.nome = nome;
    }

    public void recebeCarta(Baralho baralho){
        mao = new Carta[2];
        mao[0] = baralho.pegaCarta();
        mao[1] = baralho.pegaCarta();

    }
    public void check(){
        vida -= 10;
    }

    public void fold(){

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
    public void setBlind(int blind){
        this.blind = blind;
    }

}
