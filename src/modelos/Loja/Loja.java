package modelos.Loja;

import modelos.Jogo.Inventario;

public class Loja {
    private final Inventario inventario;
    private boolean atualizado;
    private int precoAtualizar;

    public Loja(Inventario inventario) {
        this.inventario = inventario;
        this.atualizado = false;
        this.precoAtualizar = 2;
    }

    public boolean possivelComprar(Sacola sacola) {
        boolean temDinheiro = inventario.getMoedasInventario() >= sacola.getPrecoTotal();
        boolean temEspaco = (inventario.getQtdCoringas() + sacola.getQtdCoringas()) <= inventario.getMaxCoringas();
        return temDinheiro && temEspaco;
    }

    public void comprarItens(Sacola sacola) {
        inventario.usarMoedas(sacola.getPrecoTotal());
        inventario.adicionarCoringas(sacola.getCoringasSacola());
        sacola.limparSacola();
    }

    public boolean possivelAtualizarLoja() {
        return inventario.getMoedasInventario() >= precoAtualizar && !atualizado;
    }

    public void atualizarLoja() {
        inventario.usarMoedas(precoAtualizar);
        precoAtualizar += 2;
        atualizado = true;
    }
}
