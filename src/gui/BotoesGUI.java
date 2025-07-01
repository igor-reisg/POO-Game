package gui;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;
import java.util.*;

public class BotoesGUI extends JPanel{
    private String caminhoBotao = "/assets/images/botoes/";
    private String caminhoInventario = "/assets/images/inventario/inventario.png";
    private final int largura;
    private final int altura;
    private final ImageIcon[] imagens;
    private final int numeroBotao;
    private JButton botao;
    private JLabel fundo;
    private double escalaX;
    private double escalaY;
    private String nomeAudioBotao = "/assets/sons/click_1";

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

        botao.addActionListener(e -> playButton());

        return botao;
    }

    public void setCaminhoBotao(String novoCaminho) {
        this.caminhoBotao = "/assets/images/botoes/" + novoCaminho;
        // Recria o botão com as novas imagens
        this.botao = criarBotao();
    }

    public JButton getBotao() {
        if (botao == null) {
            botao = criarBotao();
        }
        return botao;
    }

    public void playButton() {
        URL url = getClass().getResource(nomeAudioBotao + ".wav");
        AudioClip audio = Applet.newAudioClip(url);
        audio.play();
    }

    public void setBotao(JButton botao) {
        this.botao = botao;
    }
    public String getCaminho(){ return caminhoBotao; }
    public void setEscalaX(double escalaX){
        this.escalaX = escalaX;
    }
    public void setEscalaY(double escalaY){
        this.escalaY = escalaY;
    }

}
