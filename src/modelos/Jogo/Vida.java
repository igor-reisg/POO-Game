    package modelos.Jogo;

    import java.util.ArrayList;
    import java.util.List;

    public class Vida {
        int vida;
        private final List<VidaListener> listeners = new ArrayList<VidaListener>();

        public Vida(int vida) {
            this.vida = vida;
        }

        public void alterarVisualVida(){
            for(VidaListener l : listeners){
                l.vidaAlterada();
            }
        }

        public void selecionarVida(int quantidade) {
            for(VidaListener l : listeners){
                l.vidaSelecionada(quantidade);
            }
        }

        public void adicionarListener(VidaListener listener) {
            listeners.add(listener);
        }

        public int getVida() {
            return vida;
        }

        public void setVida(int vida) {
            this.vida = vida;
        }

        public void resetVida(){
            this.vida = 1500;
        }
        public List<VidaListener> getListeners() {
            return listeners;
        }


    }