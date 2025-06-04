package gui.Jogo;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import modelos.Jogo.Mesa;

public class MesaGUI extends JPanel{
    private final JLabel labelMesa;
    Mesa mesa;
    CartasPanel[] cartasMesa;

    public MesaGUI(Mesa mesa){
        this.mesa = mesa;
        labelMesa = new JLabel();
        setMinimumSize(new Dimension(1271, 720));
        labelMesa.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        cartasMesa = new CartasPanel[5];

        for(int i = 0 ; i < cartasMesa.length; i++){
            cartasMesa[i] = new CartasPanel(mesa.getCartas()[i]);
            labelMesa.add(cartasMesa[i]);
        }

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
