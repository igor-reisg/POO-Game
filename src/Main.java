import modelos.Carta;
import gui.CartasGUI;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Carta carta = new Carta(Carta.Naipe.COPAS, Carta.Valor.AS);
        CartasGUI gui = new CartasGUI(carta);

        gui.setVisible(true);

        File teste = new File("/assets/images/cartas/versos/verso.png");
        System.out.println("Consegue acessar caminho: " + teste.exists());
    }
}