package gui;
import javax.swing.*;
import java.awt.*;

public class JanelaGUI extends JFrame{
    public JanelaGUI(){
        super("Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);

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
