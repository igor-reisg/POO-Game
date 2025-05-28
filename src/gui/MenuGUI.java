package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuGUI extends JFrame implements ActionListener {
    JButton botaoJogar;
    JButton botaoCreditos;
    JButton botaoSair;
    JButton botaoOpcoes;
    JButton botaoAjuda;

    public MenuGUI() {
        super("Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tamanhoTela.width, tamanhoTela.height);

        Dimension tamanhoBotoesInferiores = new Dimension(252, 126);

        botaoJogar = new JButton();
        botaoOpcoes = new JButton("Opções");
        botaoCreditos = new JButton("Créditos");
        botaoSair = new JButton();
        botaoAjuda = new JButton();

        botaoJogar.setPreferredSize(tamanhoBotoesInferiores);
        botaoOpcoes.setPreferredSize(tamanhoBotoesInferiores);
        botaoCreditos.setPreferredSize(tamanhoBotoesInferiores);


        //PARTE DO BOTAO JOGAR
        String caminhoJogarOff = "/assets/images/botoesMenu/botaojogar_off.png";
        String caminhoJogarWhite = "/assets/images/botoesMenu/botaojogar_off_white.png";
        botaoJogar = atualizaBotaoImagem(caminhoJogarOff, 252, 126);


        //PARTE DO BOTAO SAIR
        String caminhoSairOff = "/assets/images/botoesMenu/botaoexit_off.png";
        String caminhoSairWhite = "/assets/images/botoesMenu/botaoexit_off_white.png";
        String caminhoSairOn = "/assets/images/botoesMenu/botaoexit_on.png";
        botaoSair = atualizaBotaoImagem(caminhoSairOff, 70, 70);


        //PARTE DO BOTAO AJUDA
        String caminhoAjuda = "/assets/images/botoesMenu/botaohelp_off.png";
        botaoAjuda = atualizaBotaoImagem(caminhoAjuda, 70, 70);

        JPanel menuPainel = new JPanel(new BorderLayout());

        JPanel botoesInferiores = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        botoesInferiores.add(botaoJogar);
        botoesInferiores.add(botaoOpcoes);
        botoesInferiores.add(botaoCreditos);

        botaoJogar.addActionListener(this);
        botaoOpcoes.addActionListener(this);
        botaoCreditos.addActionListener(this);


        JPanel botoesSuperiores = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        botoesSuperiores.add(botaoAjuda);
        botoesSuperiores.add(botaoSair);

        botaoSair.addActionListener(this);
        botaoAjuda.addActionListener(this);

        menuPainel.add(botoesInferiores, BorderLayout.SOUTH);
        menuPainel.add(botoesSuperiores, BorderLayout.NORTH);

        this.add(menuPainel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoJogar) {
            System.out.println("Jogar");
        }
        else if (e.getSource() == botaoOpcoes) {
            System.out.println("opcoes");
        }
        else if (e.getSource() == botaoCreditos) {
            System.out.println("Creditos");
        }
        else if (e.getSource() == botaoSair) {
            System.exit(0);
        }
        else if (e.getSource() == botaoAjuda) {
            System.out.println("Ajuda");
        }
    }

    private JButton atualizaBotaoImagem(String caminhoImagem, int largura, int altura) {
        JButton botao = new JButton();

        URL urlImagem = getClass().getResource(caminhoImagem);
        if (urlImagem != null) {
            ImageIcon icone = new ImageIcon(urlImagem);
            Image imagemRedimensionada = icone.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            botao.setIcon(new ImageIcon(imagemRedimensionada));
        }

        botao.setPreferredSize(new Dimension(largura, altura));
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);

        return botao;
    }
}

