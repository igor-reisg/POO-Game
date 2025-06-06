package gui.Jogo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class VidaGUI extends JPanel{
    private final JLabel labelVida;
    private JLabel labelVidaDiscreta;
    private int largura;
    private int altura;
    private int vida;
    private JLabel[] vidaDiscreta;
    private int vidaSelecionada = 0;

    public VidaGUI(int vida, int tipoJogador){
        this.vida = vida;
        labelVida = new JLabel();
        labelVida.setLayout(new FlowLayout(FlowLayout.RIGHT,-20,0));

        carregarImagem(labelVida, "/assets/images/botoes/jogo/barravida1.png");
        setDimensions();
        setOpaque(false);
        add(labelVida);
        vidaDiscreta = new JLabel[15];

        for(int i = 0 ; i < vidaDiscreta.length; i++){
            final int index = i;
            vidaDiscreta[index] = new JLabel();
            if(tipoJogador == 1){
                carregarImagem(vidaDiscreta[i], "/assets/images/botoes/jogo/unidadevida2.png");
            } else{
                carregarImagem(vidaDiscreta[i], "/assets/images/botoes/jogo/unidadevida3.png");
            }
            labelVida.add(vidaDiscreta[i]);
            if(tipoJogador == 1){
                vidaDiscreta[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        for(int j = 0 ; j < index ; j++){
                            carregarImagem(vidaDiscreta[j], "/assets/images/botoes/jogo/unidadevida1.png");
                        }
                        carregarImagem(vidaDiscreta[index], "/assets/images/botoes/jogo/unidadevida1.png");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        for(int j = 0 ; j < index ; j++){
                            carregarImagem(vidaDiscreta[j], "/assets/images/botoes/jogo/unidadevida2.png");
                        }
                        carregarImagem(vidaDiscreta[index], "/assets/images/botoes/jogo/unidadevida2.png");
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        vidaSelecionada = 100 * index;
                    }
                });
            }

        }
    }

    private void carregarImagem(JLabel label, String caminhoImagem) {
        try {
            URL urlImagem = getClass().getResource(caminhoImagem);
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                this.largura = (int) (img.getWidth(null));
                this.altura = (int) (img.getHeight(null));
                label.setIcon(imagem);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }

    private void setDimensions(){
        try {
            URL urlImagem = getClass().getResource("/assets/images/botoes/jogo/barravida1.png");
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da vida: " + e.getMessage());
        }
    }

    public int getLargura(){
        return largura;
    }

    public int getAltura(){
        return altura;
    }



    public void setAltura(int altura){
        this.altura = altura;
    }

}
