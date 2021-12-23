package br.com.letscode.starwarsnetwork.core.ports;

import br.com.letscode.starwarsnetwork.core.domain.Rebelde;

import java.util.List;
import java.util.Optional;

public interface RebeldeRepositorio {
    Rebelde salvar(Rebelde rebelde);
    void atualizar(Rebelde rebelde);
    Optional<Rebelde> buscarPorId(Long id);
    List<Rebelde> buscarTodosRebeldes();
}
