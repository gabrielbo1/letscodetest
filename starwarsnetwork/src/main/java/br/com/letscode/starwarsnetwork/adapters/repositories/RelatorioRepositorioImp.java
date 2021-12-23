package br.com.letscode.starwarsnetwork.adapters.repositories;

import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.RebeldeEntidadeRespositorio;
import br.com.letscode.starwarsnetwork.core.ports.RelatorioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RelatorioRepositorioImp implements RelatorioRepositorio {

    @Autowired
    private RebeldeEntidadeRespositorio rebeldeEntidadeRepositorio;

    @Override
    @Transactional
    public Long totalRebeldes() {
        return rebeldeEntidadeRepositorio.totalRebeldes();
    }

    @Override
    @Transactional
    public Long totalRebeldesTraidores() {
        return rebeldeEntidadeRepositorio.totalRebeldesTraidores();
    }

    @Override
    @Transactional
    public Long quantidadeItens(Integer codigoItem) {
        return rebeldeEntidadeRepositorio.quantidadeItens(codigoItem);
    }

    @Override
    @Transactional
    public Long quantidadeTipoItem(Integer codigoItem) {
        return rebeldeEntidadeRepositorio.quantidadeTipoItem(codigoItem);
    }

    @Override
    @Transactional
    public Long quantidadePontosPerdidosTraidores() {
        return rebeldeEntidadeRepositorio.quantidadePontosPerdidosTraidores();
    }
}
