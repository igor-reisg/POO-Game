    package modelos.Jogo;

    import java.util.ArrayList;
    import java.util.List;

    public class Vida {
        int vida;
        private final List<VidaListener> listeners = new ArrayList<VidaListener>();

        public Vida(int vida) {
            this.vida = vida;
        }

        public void perderVida(int vida){
            this.vida = this.vida - vida;
            this.vida = Math.max(this.vida, 0);
            for(VidaListener l : listeners){
                l.vidaAlterada();
            }
        }
        public void ganharVida(int vida) {
            this.vida = this.vida + vida;
            this.vida = Math.min(this.vida, 1500);
            for(VidaListener l : listeners){
                l.vidaAlterada();
            }
        }

        public void selecionarVida(int quantidade) {
            for(VidaListener l : listeners){
                l.vidaSelecionada(quantidade);
            }
        }

        public void alterarVisualVida(){
            for(VidaListener l : listeners){
                l.vidaAlterada();
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

    }