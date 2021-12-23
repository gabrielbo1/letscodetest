package br.com.letscode.starwarsnetwork.adapters.http;

import br.com.letscode.starwarsnetwork.adapters.dto.ErrorResponseDTO;
import br.com.letscode.starwarsnetwork.adapters.dto.ItemInventarioDTO;
import br.com.letscode.starwarsnetwork.adapters.dto.RebeldeDTO;
import br.com.letscode.starwarsnetwork.adapters.dto.TrocaItensDTO;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import br.com.letscode.starwarsnetwork.core.ports.RebeldeUseCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RebeldesController {

    @Autowired
    private RebeldeUseCase rebeldeUseCase;

    private RebeldeDTO rebeldeDTOParsing = new RebeldeDTO();

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ApiOperation(value = "Enpoint de Health Check")
    @ApiResponse(code = 200, message = "OK")
    public String health() {
        return "OK";
    }

    @RequestMapping(value = "/rebelde/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Buscar rebelde")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Buscar rebelde por Id", response = RebeldeDTO.class),
            @ApiResponse(code = 400, message = "Erro ao buscar rebelde por Id!", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public RebeldeDTO get(@PathVariable("id") Long id) {
        Optional<Rebelde> registro = rebeldeUseCase.buscarRebeldeComId(id);
        return registro.isPresent() ? rebeldeDTOParsing.dominioDto(registro.get()) : null;
    }

    @RequestMapping(value = "/rebelde", method = RequestMethod.GET)
    @ApiOperation(value = "Buscar todos os rebeldes.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todos os rebeldes", response = RebeldeDTO.class),
            @ApiResponse(code = 400, message = "Erro ao buscar rebelde", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public List<RebeldeDTO> get() {
        return rebeldeUseCase.listarTodosRebeldes()
                .stream()
                .map(rebeldeDTOParsing::dominioDto)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/rebelde", method = RequestMethod.POST)
    @ApiOperation(value = "Cadastrar rebelde")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rebelde cadastrado com sucesso!", response = RebeldeDTO.class),
            @ApiResponse(code = 400, message = "Erro ao cadastrar rebelde!", response =  ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public RebeldeDTO post(@RequestBody RebeldeDTO rebeldeDTO) {
        return rebeldeDTOParsing.dominioDto(rebeldeUseCase.adicionarRebelde(rebeldeDTO.dtoDominio()));
    }

    @RequestMapping(value = "/rebelde", method = RequestMethod.PUT)
    @ApiOperation(value = "Atualizar rebelde")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Localizacao do rebelde atualizada com sucesso!"),
            @ApiResponse(code = 400, message = "Erro ao atualizar rebelde!", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public void put(@RequestBody RebeldeDTO rebeldeDTO) {
        rebeldeUseCase.atualizarLocalizacaoRebelde(rebeldeDTO.dtoDominio());
    }

    @RequestMapping(value = "/rebelde/delatar/{id_delator}/{id_traidor}", method = RequestMethod.PUT)
    @ApiOperation(value = "Atualizar rebelde")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rebelde traidor delatado com sucesso!"),
            @ApiResponse(code = 400, message = "Erro ao cadastrar rebelde!", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public void delatarTraidor(@PathVariable("id_delator") Long idDelator,
                               @PathVariable("id_traidor") Long idTraidor) {
        rebeldeUseCase.reportarRebelde(idDelator, idTraidor);
    }

    @RequestMapping(value = "/rebelde/negociar", method = RequestMethod.POST)
    @ApiOperation(value = "Negociar Itens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Negociação de itens com sucesso!"),
            @ApiResponse(code = 400, message = "Erro ao negociar itens!", response = ErrorResponseDTO.class),
            @ApiResponse(code = 500, message = "Falha interna!")
    })
    public void negociarItens(@RequestBody TrocaItensDTO trocaItensDTO) {
        rebeldeUseCase.negociarItens(
            trocaItensDTO.getPrimeiroNegociador().dtoDominio(),
            trocaItensDTO.getPrimeiraLista().stream().map(ItemInventarioDTO::dtoDominio).collect(Collectors.toList()),
            trocaItensDTO.getSegundoNegociador().dtoDominio(),
            trocaItensDTO.getSegundaLista().stream().map(ItemInventarioDTO::dtoDominio).collect(Collectors.toList())
        );
    }
}
