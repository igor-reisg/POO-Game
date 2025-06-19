package modelos.Jogo;

import modelos.Cartas.*;

public class Jogador {
    private Vida vida;
    private String nome;
    private int blind;
    private int jogada;
    private Carta[] mao;
    private int dinheiro;

    public Jogador(String nome){
        vida = new Vida(1500);
        this.nome = nome;
    }

    public void limpaCartas() {
        this.mao = new Carta[2];
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
        } else {
            fold();
        }
    }

    public void check(){

    }

    public void fold(){

    }

    public void revelaCarta(int numeroDaCarta){

        mao[numeroDaCarta].virarCarta();

    }
    public Vida getVida(){
        return vida;
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
