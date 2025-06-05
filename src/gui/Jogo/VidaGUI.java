package gui.Jogo;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VidaGUI extends JPanel{
    private final JLabel labelVida;
    private int largura;
    private int altura;

    public VidaGUI(int vida){
        setBackground(Color.WHITE);
        labelVida = new JLabel();
        carregarImagem();
        add(labelVida);
        setOpaque(false);
    }

    private void carregarImagem() {
        String caminhoImagem = "/assets/images/botoes/jogo/barravida1.png";
        try {
            URL urlImagem = getClass().getResource(caminhoImagem);
            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                this.largura = (int) (img.getWidth(null));
                this.altura = (int) (img.getHeight(null));
                labelVida.setIcon(imagem);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }

    public int getLargura(){
        return largura;
    }
    public void setLargura(int largura){
        this.largura = largura;
    }
    public int getAltura(){
        return altura;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }

}
