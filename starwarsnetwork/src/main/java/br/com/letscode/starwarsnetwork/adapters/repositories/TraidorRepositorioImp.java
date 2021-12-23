package br.com.letscode.starwarsnetwork.adapters.repositories;

import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.TraidorEntidade;
import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.TraidorEntidadeRepositorio;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import br.com.letscode.starwarsnetwork.core.domain.Traidor;
import br.com.letscode.starwarsnetwork.core.ports.TraidorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraidorRepositorioImp implements TraidorRepositorio {

    @Autowired
    private TraidorEntidadeRepositorio traidorEntidadeRepositorio;

    private TraidorEntidade parsing = new TraidorEntidade();

    @Override
    public void Salvar(Traidor traidor) {
        traidorEntidadeRepositorio.save(parsing.dominioEntidade(traidor));
    }

    @Override
    public List<Traidor> buscarListaVotos(Rebelde traidor) {
        return traidorEntidadeRepositorio.findByIdTraidor(traidor.getId())
                .stream()
                .map(TraidorEntidade::entidadeDominio)
                .collect(Collectors.toList());
    }
}
