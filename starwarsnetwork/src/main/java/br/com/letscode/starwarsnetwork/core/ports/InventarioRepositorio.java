package br.com.letscode.starwarsnetwork.core.ports;

import br.com.letscode.starwarsnetwork.core.domain.ItemInventario;
import br.com.letscode.starwarsnetwork.core.domain.Item;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;

import java.util.List;

public interface InventarioRepositorio {
    void Salvar(ItemInventario itemInventario);
    void Atualizar(ItemInventario itemInventario);
    List<Item> buscarItensRebelde(Rebelde rebelde);
}
