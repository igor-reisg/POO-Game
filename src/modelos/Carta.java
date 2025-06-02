package modelos;

public class Carta {
    private final Naipe naipe;
    private final Valor valor;
    private boolean verso = true;
    private final static String imagemCaminho = "/assets/images/cartas/baralho/";

    public enum Naipe {
        COPAS, ESPADAS, OUROS, PAUS;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public enum Valor {
        DOIS(2), TRES(3), QUATRO(4), CINCO(5), SEIS(6), SETE(7), OITO(8),
        NOVE(9), DEZ(10), VALETE(11), DAMA(12), REI(13), AS(14);

        private final int valorNumerico;

        Valor(int valorNumerico) {
            this.valorNumerico = valorNumerico;
        }

        public int getValorNumerico() {
            return valorNumerico;
        }

        @Override
        public String toString() {
            return valorNumerico + "";
        }
    }

    public Carta(Naipe naipe, Valor valor) {
        this.naipe = naipe;
        this.valor = valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public Valor getValor() {
        return valor;
    }

    public void virarCarta() {
        this.verso = !this.verso;

    }

    public String getImagemCaminho() {
        if(verso) {
            return imagemCaminho + "versos/" + "cartafundo.png";
        }
        else
            return imagemCaminho + "faces/" + naipe.toString() + "_" + valor.toString() + ".png";
    }
}
