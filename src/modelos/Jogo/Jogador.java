package modelos.Jogo;

import modelos.Cartas.*;

public class Jogador {
    private int vida;
    private String nome;
    private int blind;
    private int jogada;
    private Carta[] mao;
    private int dinheiro;

    public Jogador(String nome){
        this.vida = 1500;
        this.nome = nome;
    }


    public void recebeCarta(Baralho baralho){
        mao = new Carta[2];
        mao[0] = baralho.pegaCarta();
        mao[1] = baralho.pegaCarta();

    }
    public void escolhaDaJogada(int escolha){
        this.jogada = escolha;
        if(escolha == 1){
            check();
        } else{
            fold();
        }
    }

    public void check(){
        vida -= 10;
        System.out.println(vida);
    }

    public void fold(){

    }

    public int getVida(){
        return vida;
    }
    public void setVida(int vida){
        this.vida = vida;
    }

    public Carta[] getMao(){
        return mao;
    }
    public void setMao(Carta[] mao){
        this.mao = mao;
    }
    public void setBlind(int blind){
        this.blind = blind;
    }
    public int getBlind(){
        return blind;
    }
    public int getDinheiro(){
        return dinheiro;
    }
    public void setDinheiro(int dinheiro){
        this.dinheiro = dinheiro;
    }
    public int getJogada(){
        return jogada;
    }
    public void setJogada(int jogada){
        this.jogada = jogada;
    }

}
