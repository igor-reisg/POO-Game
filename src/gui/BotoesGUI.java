package gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BotoesGUI extends JPanel{
    String caminhoBotao = "/assets/images/botoes/";
    int largura;
    int altura;
    ImageIcon[] Imagens;
    int numeroBotao;
    JButton botao;

    public BotoesGUI(String caminhoBase, int altura, int largura, int numeroBotao){
        this.altura = altura;
        this.largura = largura;
        caminhoBotao += caminhoBase;
        this.numeroBotao = numeroBotao;
        this.Imagens = new ImageIcon[3];
    }

    private JButton criarBotao() {
        for (int i = 0; i < Imagens.length; i++){
            Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource(caminhoBotao + numeroBotao + "_" + i + ".png"))).getImage();
            img = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            Imagens[i] = new ImageIcon(img);
        }

        botao = new JButton(Imagens[0]);
        botao.setPreferredSize(new Dimension(largura, altura));
        botao.setRolloverIcon(Imagens[1]);
        botao.setPressedIcon(Imagens[2]);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        return botao;
    }

    public JButton getBotao() {
        if (botao == null) {
            botao = criarBotao();
        }
        return botao;
    }

}
