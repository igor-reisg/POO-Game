package modelos.Loja;

import modelos.Jogo.Inventario;

public class Loja {
    Inventario inventario;
    boolean atualizado = false;
    int precoAtualizar;

    public Loja(Inventario inventario) {
        this.inventario = inventario;
        precoAtualizar = 2;
    }

    public boolean possivelAtualizarLoja() {
        if (inventario.getMoedasInventario() >= precoAtualizar && !atualizado) {
            return true;
        }
        return false;
    }

    public void atualizarLoja() {
        inventario.usarMoedas(precoAtualizar);
        precoAtualizar += 2;
        atualizado = true;
    }

}
