package gui;
import gui.Menu.MenuGUI;

import javax.swing.*;
import java.awt.*;

public class JanelaGUI extends JFrame{
    public JanelaGUI(){
        super("Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setUndecorated(true);

        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tamanhoTela.width, tamanhoTela.height);

        setContentPane(new MenuGUI(this));
    }
    

    public void trocarTela(JPanel novaTela){
        getContentPane().removeAll();
        setContentPane(novaTela);
        revalidate();
        repaint();
    }
}
