package br.com.letscode.starwarsnetwork.adapters.http;

import br.com.letscode.starwarsnetwork.core.domain.Item;
import br.com.letscode.starwarsnetwork.core.usecases.RelatorioCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class RelatorioController {

    @Autowired
    private RelatorioCase relatorioCase;

    @RequestMapping(value = "/porcentagem/traidor", method = RequestMethod.GET)
    @ApiOperation(value = "Relatório porcentagem de traidores.")
    @ApiResponse(code = 200, message = "10")
    public BigDecimal porcentagemTraidores() {
        return relatorioCase.porcentagensTraidores();
    }

    @RequestMapping(value = "/porcentagem/rebelde", method = RequestMethod.GET)
    @ApiOperation(value = "Relatório porcentagem de rebeldes.")
    @ApiResponse(code = 200, message = "10")
    public BigDecimal porcentagemRebelde() {
        return relatorioCase.porcentagensRebeldes();
    }

    @RequestMapping(value = "/media/recurso", method = RequestMethod.GET)
    @ApiOperation(value = "Quantidade média de recursos")
    @ApiResponse(code = 200, message = "10")
    public Map<Item, BigDecimal> qtdMediaRecurso() {
        return relatorioCase.quatidadeMediaRecurso();
    }

    @RequestMapping(value = "/pontos/traidores", method = RequestMethod.GET)
    @ApiOperation(value = "Pontos perdidos por conta de traidores.")
    @ApiResponse(code = 200, message = "10")
    public BigDecimal porcentagemRebeldes() {
        return relatorioCase.pontosPerdidosTraidores();
    }
}
