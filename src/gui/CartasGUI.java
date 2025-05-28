package gui;

import modelos.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class CartasGUI extends JFrame implements ActionListener {
    private final Carta carta;
    private final JLabel labelCarta;
    private final JButton botaoVirar;

    public CartasGUI(Carta carta) {
        super("Carta");
        this.carta = carta;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painelCarta = new JPanel(new FlowLayout());
        labelCarta = new JLabel();
        carregarImagem();
        painelCarta.add(labelCarta);
        add(painelCarta, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout());
        botaoVirar = new JButton("Virar");
        botaoVirar.addActionListener(this);
        painelBotao.add(botaoVirar);
        add(painelBotao, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarImagem() {
        try {
            String caminhoImagem = carta.getImagemCaminho();
            URL urlImagem = getClass().getResource(caminhoImagem);

            if(urlImagem != null) {
                ImageIcon imagem = new ImageIcon(urlImagem);
                labelCarta.setIcon(imagem);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoVirar) {
            carta.virarCarta();
            carregarImagem();
            System.out.println("Virando carta");
        }
    }
}