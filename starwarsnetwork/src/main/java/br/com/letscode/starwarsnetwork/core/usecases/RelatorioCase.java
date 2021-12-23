package br.com.letscode.starwarsnetwork.core.usecases;

import br.com.letscode.starwarsnetwork.core.domain.Item;
import br.com.letscode.starwarsnetwork.core.ports.RelatorioRepositorio;
import br.com.letscode.starwarsnetwork.core.ports.RelatorioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RelatorioCase implements RelatorioUseCase {

    @Autowired
    private RelatorioRepositorio relatorioRepositorio;

    @Override
    @Transactional
    public BigDecimal porcentagensTraidores() {
        Long qtdRebeldes = relatorioRepositorio.totalRebeldes();
        Long qtdTraidores = relatorioRepositorio.totalRebeldesTraidores();
        if (qtdTraidores == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf((qtdTraidores.doubleValue() / 100)
                * qtdRebeldes.doubleValue());
    }

    @Override
    @Transactional
    public BigDecimal porcentagensRebeldes() {
        Long qtdRebeldes = relatorioRepositorio.totalRebeldes();
        if (qtdRebeldes == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(100).subtract(porcentagensTraidores());
    }

    @Override
    public Map<Item, BigDecimal> quatidadeMediaRecurso() {
        Map<Item, BigDecimal> qtdMediaRecurso = new HashMap<>();
        for (Item item : Item.values()) {
            Long qtdTipoItem = relatorioRepositorio.quantidadeTipoItem(item.getCodigo());
            Long qtdItem = relatorioRepositorio.quantidadeItens(item.getCodigo());

            if (Objects.isNull(qtdItem) || qtdItem == 0) {
                qtdMediaRecurso.put(item, BigDecimal.ZERO);
                continue;
            }
            qtdMediaRecurso.put(item, BigDecimal.valueOf(qtdItem.doubleValue() / qtdTipoItem.doubleValue()));
        }
        return qtdMediaRecurso;
    }

    @Override
    public BigDecimal pontosPerdidosTraidores() {
        Long pontos = relatorioRepositorio.quantidadePontosPerdidosTraidores();
        return pontos == null || pontos == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(pontos);
    }
}
