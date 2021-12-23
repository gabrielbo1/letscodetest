package br.com.letscode.starwarsnetwork.core.ports;

import br.com.letscode.starwarsnetwork.core.domain.Item;

import java.math.BigDecimal;
import java.util.Map;

public interface RelatorioUseCase {
    BigDecimal porcentagensTraidores();
    BigDecimal porcentagensRebeldes();
    Map<Item, BigDecimal> quatidadeMediaRecurso();
    BigDecimal pontosPerdidosTraidores();
}