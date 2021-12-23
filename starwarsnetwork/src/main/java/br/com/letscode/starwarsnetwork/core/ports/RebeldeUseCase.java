package br.com.letscode.starwarsnetwork.core.ports;

import br.com.letscode.starwarsnetwork.core.domain.ItemInventario;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;

import java.util.List;
import java.util.Optional;

public interface RebeldeUseCase {
    Optional<Rebelde> buscarRebeldeComId(Long id);
    List<Rebelde> listarTodosRebeldes();
    Rebelde adicionarRebelde(Rebelde rebelde);
    void atualizarLocalizacaoRebelde(Rebelde rebelde);
    void reportarRebelde(Long idDelator, Long idTraidor);
    void negociarItens(Rebelde primeiroNegociador, List<ItemInventario> primeiraLista,
                       Rebelde segundoNegociador, List<ItemInventario> segundaLista);
}
