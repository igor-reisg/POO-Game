package gui.Loja;

import gui.BotoesGUI;
import gui.JanelaGUI;

import javax.swing.*;
import java.awt.*;

public class LojaGUI extends JFrame {
    JanelaGUI app;
    BotoesGUI[] BotoesLoja;

    public LojaGUI(JanelaGUI app) {
        this.app = app;

        BotoesLoja = new BotoesGUI[3];
        for (int i = 0; i < BotoesLoja.length; i++) {
            BotoesLoja[i] = new BotoesGUI("loja/", 95, 189, i);
        }

        JPanel lojaPainel = new JPanel(new BorderLayout());

        JPanel painelInferiorDireito = new JPanel();
        painelInferiorDireito.setLayout(new BoxLayout(painelInferiorDireito, BoxLayout.Y_AXIS));
        for (int i = 0; i < 2; i++) {
            painelInferiorDireito.add(BotoesLoja[i].getBotao());
        }

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new FlowLayout());
        painelDireita.add(painelInferiorDireito, BorderLayout.SOUTH);

        lojaPainel.add(painelDireita, BorderLayout.EAST);
    }
}
