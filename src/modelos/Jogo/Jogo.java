package modelos.Jogo;

import java.util.Random;
import modelos.Cartas.*;

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

        System.out.println("Rodada " + rodada++);

        jogador.recebeCarta(baralho);
        inimigo.recebeCarta(baralho);

        inimigoPronto = false;
        jogadorPronto = false;

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
        }
    }

    public void iniciarJogo() {

        if (jogador.getVida() <= 0 || inimigo.getVida() <= 0) {
            finalizarJogo();
            return;
        }

        Random r = new Random();
        int moeda = r.nextInt(2);

        jogador.setBlind(moeda);
        inimigo.setBlind(1 - moeda);

        while(jogador.getVida() <= 0 || inimigo.getVida() <= 0){

            iniciarRodada();
            novaRodada();

        }
    }

    private void novaRodada(){
        rodada++;

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

        int contTurnos = 0;

        while(contTurnos <=2){


            if(jogador.getBlind() == 1){
                registrarEscolhaJogador(jogador.getJogada());
                registrarEscolhaInimigo();
            } else {
                registrarEscolhaInimigo();
                registrarEscolhaJogador(jogador.getJogada());
            }

            //Inverte os blinds
            jogador.setBlind(1 - jogador.getBlind());
            inimigo.setBlind(1 - inimigo.getBlind());
            pote.resetPote();

            if(contTurnos == 0){
                mesa.revelaCarta(0);
                mesa.revelaCarta(1);
                mesa.revelaCarta(2);

            } if(contTurnos == 1){
                mesa.revelaCarta(3);
            } if(contTurnos == 2) {
                mesa.revelaCarta(4);
            }
            contTurnos++;

        }
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
