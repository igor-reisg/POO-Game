package gui.Jogo;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Objects;

public class IconeGUI extends JPanel{
    Border borda;
    JLabel labelIcone;

    public IconeGUI(){
        setBackground(Color.WHITE);
         labelIcone = new JLabel();


        carregarImagem();
        add(labelIcone);
    }

    private void carregarImagem() {
        try {
            String caminhoImagem = "/assets/images/framePrincipal/frame01.png";
            URL urlImagem = getClass().getResource(caminhoImagem);

            if (urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                Image img = imagem.getImage();
                int largura = (int) (img.getWidth(null) * 1.1);
                int altura = (int) (img.getHeight(null) * 1.1);
                Image imagemRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                labelIcone.setIcon(new ImageIcon(imagemRedimensionada));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }
    }

}