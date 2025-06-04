package modelos.Jogo;

import java.util.Random;
import modelos.Cartas.*;

import javax.swing.*;

public class Jogo {
    int level;
    int fase;
    int rodada;

    Baralho baralho;
    Pote pote;
    Jogador jogador;
    Inimigo inimigo;
    Mesa mesa;

    private boolean jogadorPronto;
    private boolean inimigoPronto;
    private int jogadaJogador;
    private int jogadaInimigo;

    public Jogo(){

        this.rodada = 1;
        this.fase = 1;
        this.level = 1;

        pote = new Pote();
        inimigo = new Inimigo();
        jogador = new Jogador("Naka");
        baralho = new Baralho();
        baralho.criaBaralho();
        baralho.embaralhaBaralho();

        mesa = new Mesa(baralho);
        mesa.recebeCarta(baralho);
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);
    }

    public void iniciarRodada(){
        mesa.recebeCarta(baralho);
        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        inimigoPronto = false;
        jogadorPronto = false;

        Random r = new Random();
        int moeda = r.nextInt(2);
        jogador.setBlind(moeda);
        inimigo.setBlind(1 - moeda);

    }

    public void registrarEscolhaJogador(int escolha) {
        this.jogadaJogador = escolha;
        jogadorPronto = true;
        verificarSePodeAvancar();
    }

    public void registrarEscolhaInimigo() {
        this.jogadaInimigo = inimigo.getJogada(); // IA decide
        inimigoPronto = true;
        verificarSePodeAvancar();
    }

    public void verificarSePodeAvancar(){
        if(inimigoPronto && jogadorPronto){
            processarRodada();
        }
    }

    public void processarRodada(){
        if(jogadaJogador == 0){
            jogador.setVida(jogador.getVida() - pote.getQuantidade());
        } else if(jogadaInimigo == 0){
            inimigo.setVida(jogador.getVida() - pote.getQuantidade());
        } else{
            novaRodada();
        }
    }

    public void iniciarJogo() {
        if (jogador.getVida() <= 0 || inimigo.getVida() <= 0) {
            finalizarJogo();
            return;
        }
        System.out.println("Rodada " + rodada++);
        mesa.revelaCarta();
        mesa.revelaCarta();
        mesa.revelaCarta();

        novaRodada();
    }

    private void novaRodada(){
        rodada++;
        mesa.revelaCarta();

        if(jogador.getBlind() == 1){
            jogador.setVida(jogador.getVida() - 200);
            pote.setQuantidade(200);
            inimigo.setVida(inimigo.getVida() - 100);
            pote.setQuantidade(100);

        } else {
            jogador.setVida(jogador.getVida() - 100);
            pote.setQuantidade(200);
            inimigo.setVida(inimigo.getVida() - 200);
            pote.setQuantidade(100);
        }

        //Inverte os blinds
        jogador.setBlind(1 - jogador.getBlind());
        inimigo.setBlind(1 - inimigo.getBlind());
        pote.resetPote();
    }


    public void finalizarJogo(){
        if(jogador.getVida() <= 0){
            System.out.println("Você perdeu!");
        } else if (inimigo.getVida() <= 0) {
            System.out.println("Você venceu!");
        }
    }

    public Jogador getJogador(){
        return jogador;
    }
    public Inimigo getInimigo(){
        return inimigo;
    }
    public Mesa getMesa(){
        return mesa;
    }
    public  Pote getPote(){
        return pote;
    }
}
