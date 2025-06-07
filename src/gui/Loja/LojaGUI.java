package gui.Loja;

import gui.BackgroundPanel;
import gui.BotoesGUI;
import gui.JanelaGUI;
import gui.Jogo.CoringasGUI;
import gui.Jogo.MesaGUI;
import modelos.Cartas.Coringa;
import modelos.Cartas.CoringaReader;
import modelos.Jogo.Inventario;
import modelos.Jogo.Jogo;
import modelos.Jogo.Loja;
import modelos.Jogo.MesaLoja;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class LojaGUI extends JPanel {
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    String caminhoCoringas = "/assets/data/coringas.json";
    int locationX = 100;
    int locationY = 100;
    int largura;
    int altura;
    private Dimension tamanhoTela;
    MesaLojaGUI mesa;

    public LojaGUI(JanelaGUI app, Inventario inventario) {
        this.app = app;
        tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraTela = tamanhoTela.width;
        int alturaTela = tamanhoTela.height;

        //Geração dos botões da loja
        BotoesLoja = new BotoesGUI[3];
        for (int i = 0; i < BotoesLoja.length; i++) {
            BotoesLoja[i] = new BotoesGUI("loja/", 95, 189, i);
        }

        JPanel lojaPainel = new JPanel(new BorderLayout());
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(lojaPainel, BorderLayout.CENTER);

        //Imagens das mesas ?????????????????????????????????????????????????????????????????????????????????? ESSA BOSTA NAO TA FUNCIONANDO DIREITO
        mesa = new MesaLojaGUI(new MesaLoja());
        Dimension mesaSize = mesa.getPreferredSize();
        mesa.setBounds((int) (larguraTela/2 - ( mesaSize.getWidth()/2) ) , (int) (alturaTela/2 - mesaSize.getHeight()/2),  mesaSize.width, mesaSize.height);
        mesa.setOpaque(false);
        background.add(mesa);

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BorderLayout());

        //Tamanho dos Coringas
        largura = 176;
        altura = 248;

        //Panel dos Coringas
        JLayeredPane layerCoringas = new JLayeredPane();
        layerCoringas.setOpaque(false);
        layerCoringas.setSize(largura, altura);

        try {
            List<Coringa> coringasData = CoringaReader.CoringaRead(caminhoCoringas).getCoringas();
            for (Coringa coringaData : coringasData) {
                CoringasGUI coringaGUI = new CoringasGUI(coringaData, locationX, locationY, largura, altura);
                layerCoringas.add(coringaGUI, JLayeredPane.DRAG_LAYER);
                locationX += 250;
            }
        } catch (Exception e) {}

        JPanel painelInferiorDireito = new JPanel();
        painelInferiorDireito.setLayout(new BoxLayout(painelInferiorDireito, BoxLayout.Y_AXIS));
        for (int i = 0; i < 2; i++) {
            painelInferiorDireito.add(BotoesLoja[i].getBotao());
        }
        painelDireita.add(painelInferiorDireito, BorderLayout.SOUTH);

        background.add(painelDireita, BorderLayout.EAST);
        background.add(layerCoringas, BorderLayout.CENTER);

        painelDireita.setOpaque(false);
        painelInferiorDireito.setOpaque(false);
        lojaPainel.setOpaque(false);
        layerCoringas.setOpaque(false);

        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
        setVisible(true);
    }
}
