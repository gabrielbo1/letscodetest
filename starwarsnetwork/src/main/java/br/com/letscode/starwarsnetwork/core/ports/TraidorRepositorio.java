package br.com.letscode.starwarsnetwork.core.ports;

import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import br.com.letscode.starwarsnetwork.core.domain.Traidor;

import java.util.List;

public interface TraidorRepositorio {
    void Salvar(Traidor traidor);
    List<Traidor> buscarListaVotos(Rebelde traidor);
}
