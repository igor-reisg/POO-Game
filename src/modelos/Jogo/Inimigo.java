package modelos.Jogo;

import modelos.Cartas.Baralho;
import modelos.Cartas.Carta;

public class Inimigo {
    private int vida;
    private int jogada;
    private String nome;
    private Carta[] mao;
    private int dinheiro;
    private int blind;

    public Inimigo(){
        this.vida = 1500;
    }

    public void escolhaDaJogada(int escolha){
        this.jogada = escolha;
        if(escolha == 1){
            check();
        } else{
            fold();
        }
    }
    public void limpaCartas() {
        this.mao = new Carta[2];
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
    public void setVida(int vida){ this.vida = vida; }
    public Carta[] getMao(){
        return mao;
    }
    public void setMao(Carta[] mao){ this.mao = mao; }
    public int getDinheiro(){
        return dinheiro;
    }
    public void setDinheiro(int dinheiro){ this.dinheiro = dinheiro; }
    public int getBlind(){ return blind; }
    public void setBlind(int blind){ this.blind = blind; }
    public int getJogada(){
        return 1;//jogada
    }
    public void setJogada(int jogada){ this.jogada = jogada; }
}
