package gui.Loja;

import gui.BackgroundPanel;
import gui.BotoesGUI;
import gui.JanelaGUI;
import gui.Jogo.CoringasGUI;
import modelos.Cartas.Coringa;
import modelos.Cartas.CoringaReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class LojaGUI extends JPanel {
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;
    String caminhoBackground = "/assets/images/background/pattern2.png";
    String caminhoCoringas = "/assets/data/coringas.json";
    CoringasGUI coringa;
    List<CoringasGUI> coringas;
    CoringaReader lerCoringas;

    public LojaGUI(JanelaGUI app) throws IOException {
        this.app = app;

        BotoesLoja = new BotoesGUI[3];
        for (int i = 0; i < BotoesLoja.length; i++) {
            BotoesLoja[i] = new BotoesGUI("loja/", 95, 189, i);
        }

        JPanel lojaPainel = new JPanel(new BorderLayout());
        BackgroundPanel background = new BackgroundPanel(caminhoBackground);
        background.add(lojaPainel, BorderLayout.CENTER);

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new FlowLayout());

        JPanel panelCoringas = new JPanel(new FlowLayout());
        List<Coringa> coringasData = CoringaReader.CoringaRead(caminhoCoringas).getCoringas();
        for (Coringa coringaData : coringasData) {
            CoringasGUI coringaGUI = new CoringasGUI(coringaData);
            panelCoringas.add(coringaGUI);
        }

        JPanel painelInferiorDireito = new JPanel();
        painelInferiorDireito.setLayout(new BoxLayout(painelInferiorDireito, BoxLayout.Y_AXIS));
        for (int i = 0; i < 2; i++) {
            painelInferiorDireito.add(BotoesLoja[i].getBotao());
        }
        painelDireita.add(painelInferiorDireito, BorderLayout.SOUTH);
        painelDireita.add(panelCoringas, BorderLayout.NORTH);

        lojaPainel.add(painelDireita, BorderLayout.EAST);

        painelDireita.setOpaque(false);
        painelInferiorDireito.setOpaque(false);
        lojaPainel.setOpaque(false);

        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);
        setVisible(true);
    }
}
