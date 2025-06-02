package gui;
import javax.swing.*;
import javax.swing.border.Border;

import modelos.Player;
import modelos.Enemy;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Objects;

public class IconeGUI extends JPanel{
    Border borda;

    public IconeGUI(){
        setBackground(Color.WHITE);
        borda = BorderFactory.createLineBorder(Color.BLUE, 3);
        setBorder(borda);
    
    }


    private void alteraIcon(){

    }
}