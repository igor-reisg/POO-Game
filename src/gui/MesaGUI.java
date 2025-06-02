package gui;
import modelos.Carta;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MesaGUI extends JPanel{
    private final JLabel labelMesa;

    public MesaGUI(){
        
        labelMesa = new JLabel();
        setMinimumSize(new Dimension(1271, 720));
        carregarImagem();
        add(labelMesa);
    }

    private void carregarImagem() {
    try {
        String caminhoImagem = "/assets/images/mesaPrincipal/campotiled.png";
        URL urlImagem = getClass().getResource(caminhoImagem);

        if (urlImagem != null) {
            ImageIcon imagem = new ImageIcon(urlImagem);
            Image img = imagem.getImage();
            int largura = (int) (img.getWidth(null) * 1.1);
            int altura = (int) (img.getHeight(null) * 1.1);
            Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            labelMesa.setIcon(new ImageIcon(imagemRedimensionada));
        }
    } catch (Exception e) {
        System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
    }
}

}
