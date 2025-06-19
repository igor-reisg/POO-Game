package modelos.Jogo;

public class Round {
    int round;

    public Round(){
        this.round = 1;
    }
    public void setRound(int round){
        this.round = round;
    }
    public void passaRound(){
        this.round++;
    }
    public int getRound(){
        return round;
    }
}
