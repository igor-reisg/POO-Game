package gui.Jogo;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class IconeGUI extends JPanel{
    Border borda;
    String caminhoImagemBorda = "/assets/images/frames/framePrincipal/frame01.png";
    String caminhoImagem;
    JLabel labelIcone, labelNomeJogador, labelIconeJogador;
    private int largura;
    private int altura;


    public IconeGUI(String caminhoImagem, String nome){
        this.caminhoImagem = caminhoImagem;
        Font fontePersonalizada;
        setOpaque(false);
        labelIcone = new JLabel();
        labelNomeJogador = new JLabel(nome);
        labelIconeJogador = new JLabel();


        File fonteArquivo = new File("src/assets/fonts/RetroGaming.ttf");
        try{
            fontePersonalizada = Font.createFont(Font.TRUETYPE_FONT, fonteArquivo).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontePersonalizada);
        } catch(FontFormatException | IOException e) {
             fontePersonalizada = new Font("Arial", Font.PLAIN, 24);
        }
        carregarImagem(caminhoImagemBorda, labelIcone);
        add(labelIcone);
        labelIcone.setLayout(null);

        labelNomeJogador.setBounds(10, 10, 170, 20);
        labelNomeJogador.setFont(fontePersonalizada);
        labelIcone.add(labelNomeJogador);

        labelIconeJogador.setBounds(10, 40, 170, 120);
        carregarImagem(caminhoImagem, labelIconeJogador);
        labelIcone.add(labelIconeJogador);

    }


    private void carregarImagem(String caminhoImagem, JLabel destino) {
        URL urlImagem = getClass().getResource(caminhoImagem);
        if (urlImagem == null) {
            System.err.println("Imagem não encontrada: " + caminhoImagem);
            return;
        }

        ImageIcon imagem = new ImageIcon(urlImagem);
        Image img = imagem.getImage();
        this.largura = img.getWidth(null);
        this.altura = img.getHeight(null);
        destino.setIcon(imagem);
    }

    public Dimension getPreferredSize(JLabel x) {
        return new Dimension(largura, altura);
    }
}
