package br.com.letscode.starwarsnetwork.adapters.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrocaItensDTO {

    @ApiModelProperty(name = "primeiroNegociador")
    private RebeldeDTO primeiroNegociador;

    @ApiModelProperty(name = "primeiraLista")
    private List<ItemInventarioDTO> primeiraLista;

    @ApiModelProperty(name = "segundoNegociador")
    private RebeldeDTO segundoNegociador;

    @ApiModelProperty(name = "segundaLista")
    private List<ItemInventarioDTO> segundaLista;
}
