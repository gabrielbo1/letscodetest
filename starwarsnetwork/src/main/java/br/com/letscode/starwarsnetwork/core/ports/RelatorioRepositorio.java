package br.com.letscode.starwarsnetwork.core.ports;

public interface RelatorioRepositorio {
    Long totalRebeldes();
    Long totalRebeldesTraidores();
    Long quantidadeItens(Integer codigoItem);
    Long quantidadeTipoItem(Integer codigoItem);
    Long quantidadePontosPerdidosTraidores();
}
