package br.com.letscode.starwarsnetwork.adapters.dto;

import br.com.letscode.starwarsnetwork.core.domain.Genero;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class RebeldeDTO implements ParserDTO<Rebelde> {

    @ApiModelProperty(value = "1", name = "id", dataType = "Long", example = "200")
    private Long id;

    @ApiModelProperty(value = "Gabriel", name = "nome", dataType = "String", example = "Marcos Xin")
    private String nome;

    @ApiModelProperty(value = "10", name = "idade", dataType = "Integer", example = "20")
    private Integer idade;

    @ApiModelProperty(value = "MASCULINO", name = "genero", dataType = "Genero", example = "MASCULINO", notes = "Valores aceitos: MASCULINO, FEMININO, NAO_INFORMADO")
    private Genero genero;

    @ApiModelProperty(value = "20.0000", name = "latitude", dataType = "Float", example = "20.0000")
    private BigDecimal latitude;

    @ApiModelProperty(value = "36.0000", name = "longitude", dataType = "Float", example = "36.0000")
    private BigDecimal longitude;

    @ApiModelProperty(value = "Lactea", name = "nomeGalaxia", dataType = "String", example = "Lactea")
    private String nomeGalaxia;

    @ApiModelProperty(value = "false", name = "traidor", dataType = "Boolean", example = "false")
    private Boolean traidor;

    @ApiModelProperty(name = "itens")
    private List<ItemInventarioDTO> itens;

    @Override
    public Rebelde dtoDominio() {
        return Rebelde
                .builder()
                .id(id)
                .nome(nome)
                .idade(idade)
                .genero(genero)
                .latitude(latitude)
                .longitude(longitude)
                .nomeGalaxia(nomeGalaxia)
                .traidor(traidor)
                .itens(Objects.nonNull(itens) ? itens.stream().map(ItemInventarioDTO::dtoDominio).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    @Override
    public RebeldeDTO dominioDto(Rebelde dominio) {
        RebeldeDTO rebeldeDTO = new RebeldeDTO();
        rebeldeDTO.id = dominio.getId();
        rebeldeDTO.nome = dominio.getNome();
        rebeldeDTO.idade = dominio.getIdade();
        rebeldeDTO.genero = dominio.getGenero();
        rebeldeDTO.latitude = dominio.getLatitude();
        rebeldeDTO.longitude = dominio.getLongitude();
        rebeldeDTO.nomeGalaxia = dominio.getNomeGalaxia();
        rebeldeDTO.traidor = dominio.getTraidor();
        ItemInventarioDTO itemInventarioDTO = new ItemInventarioDTO();
        rebeldeDTO.itens = dominio.getItens().stream()
                .map(itemInventarioDTO::dominioDto)
                .collect(Collectors.toList());
        return rebeldeDTO;
    }
}
