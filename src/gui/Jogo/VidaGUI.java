package gui.Jogo;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class VidaGUI extends JPanel{
    Border borda;

    public VidaGUI(){
        setBackground(Color.WHITE);
        borda = BorderFactory.createLineBorder(Color.BLACK, 3);
        setBorder(borda);
    }
}
