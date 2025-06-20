package gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BotoesGUI extends JPanel{
    private String caminhoBotao = "/assets/images/botoes/";
    private final int largura;
    private final int altura;
    private final ImageIcon[] imagens;
    private final int numeroBotao;
    private JButton botao;
    private double escalaX;
    private double escalaY;

    public BotoesGUI(String caminhoBase, int altura, int largura, int numeroBotao){
        escalaX = 1;
        escalaY = 1;
        this.altura = altura;
        this.largura = largura;
        caminhoBotao += caminhoBase;
        this.numeroBotao = numeroBotao;
        this.imagens = new ImageIcon[3];
    }

    private JButton criarBotao() {
        for (int i = 0; i < imagens.length; i++){
            Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource(caminhoBotao + numeroBotao + "_" + i + ".png"))).getImage();
            img = img.getScaledInstance((int) (largura * escalaX), (int) (altura * escalaY), Image.SCALE_SMOOTH);
            imagens[i] = new ImageIcon(img);
        }

        botao = new JButton(imagens[0]);
        botao.setPreferredSize(new Dimension((int)(largura * escalaX),(int) (altura * escalaY)));
        botao.setRolloverIcon(imagens[1]);
        botao.setPressedIcon(imagens[2]);
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
    public void setBotao(JButton botao) {
        this.botao = botao;
    }
    public void setEscalaX(double escalaX){
        this.escalaX = escalaX;
    }
    public void setEscalaY(double escalaY){
        this.escalaY = escalaY;
    }

}
