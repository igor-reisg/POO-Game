package gui.Jogo;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import modelos.Jogo.Mesa;

public class MesaGUI extends JPanel{
    private final JLabel labelMesa;
    Mesa mesa;
    CartasPanel[] cartasMesa;
    int largura, altura;
    public MesaGUI(Mesa mesa){
        this.mesa = mesa;
        labelMesa = new JLabel();
        setMinimumSize(new Dimension(1271, 720));
        labelMesa.setLayout(null);

        carregarImagem();

        cartasMesa = new CartasPanel[5];
        int espacamento = 150;
        int xInicial = largura / 2 - (cartasMesa.length * espacamento) / 2 + 15;

        for(int i = 0 ; i < cartasMesa.length; i++){
            cartasMesa[i] = new CartasPanel(mesa.getCartas()[i]);
            cartasMesa[i].setBounds(xInicial + i * espacamento, altura / 2 - cartasMesa[0].getHeight() / 2, cartasMesa[0].getWidth(), cartasMesa[0].getHeight());
            labelMesa.add(cartasMesa[i]);
        }

        add(labelMesa);
    }

    private void carregarImagem() {
        try {
            String caminhoImagem = "/assets/images/mesaPrincipal/campo.png";
            URL urlImagem = getClass().getResource(caminhoImagem);

            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                largura = (int) (img.getWidth(null) * 1.1);
                altura = (int) (img.getHeight(null) * 1.1);
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                labelMesa.setIcon(new ImageIcon(imagemRedimensionada));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da mesa: " + e.getMessage());
        }
    }

}
