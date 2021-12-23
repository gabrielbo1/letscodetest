package br.com.letscode.starwarsnetwork.adapters.repositories;

import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.RebeldeEntidade;
import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.RebeldeEntidadeRespositorio;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import br.com.letscode.starwarsnetwork.core.ports.RebeldeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RebeldeRepositorioImp implements RebeldeRepositorio {

    @Autowired
    private RebeldeEntidadeRespositorio rebeldeEntidadeRespositorio;

    @Override
    @Transactional
    public Rebelde salvar(Rebelde rebelde) {
        RebeldeEntidade entidade = RebeldeEntidade.builder().build().dominioEntidade(rebelde);
        entidade.setId(null);
        RebeldeEntidade registro = rebeldeEntidadeRespositorio.saveAndFlush(entidade);
        return registro.entidadeDominio();
    }

    @Override
    @Transactional
    public void atualizar(Rebelde rebelde) {
        rebeldeEntidadeRespositorio.save(RebeldeEntidade.builder().build().dominioEntidade(rebelde));
    }

    @Override
    @Transactional
    public Optional<Rebelde> buscarPorId(Long id) {
        Optional<RebeldeEntidade> registro = rebeldeEntidadeRespositorio.findById(id);
        return registro.isPresent() ? Optional.of(registro.get().entidadeDominio()) : Optional.empty();
    }

    @Override
    public List<Rebelde> buscarTodosRebeldes() {
        List<RebeldeEntidade> rebeldes =  rebeldeEntidadeRespositorio.findAll();
        for (RebeldeEntidade rebelde : rebeldes)
            rebelde.setItens(rebelde.getItens());

        return rebeldes
                .stream()
                .map(RebeldeEntidade::entidadeDominio)
                .collect(Collectors.toList());
    }
}
