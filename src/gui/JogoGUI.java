package gui;
import java.awt.*;
import javax.swing.*;


public class JogoGUI extends JPanel {
    JanelaGUI app;
    
    public JogoGUI(JanelaGUI app){
        this.app = app;
        
        this.setLayout(new BorderLayout());
        JPanel mesa = new JPanel();
        mesa.setBackground(Color.BLUE);
        
        this.add(mesa, BorderLayout.CENTER);

        revalidate();
        repaint();
        setVisible(true);
    }

    


}
