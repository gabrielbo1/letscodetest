package br.com.letscode.starwarsnetwork.adapters.dto;

import br.com.letscode.starwarsnetwork.core.domain.Item;
import br.com.letscode.starwarsnetwork.core.domain.ItemInventario;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ItemInventarioDTO implements ParserDTO<ItemInventario> {

    @ApiModelProperty(value = "1", name = "id", dataType = "Long", example = "200")
    private Long id;

    @ApiModelProperty(value = "ARMA", name = "item", dataType = "Item", example = "ARMA", notes = "Valores aceitos: ARMA, MUNICAO, AGUA, COMIDA")
    private Item item;

    @ApiModelProperty(value = "100", name = "pontos", dataType = "Integer", example = "4")
    private Integer pontos;

    @ApiModelProperty(value = "25", name = "pontos", dataType = "Integer", example = "16")
    private Integer quantidade;

    @Override
    public ItemInventario dtoDominio() {
        return ItemInventario
                .builder()
                .id(id)
                .item(item)
                .pontos(pontos)
                .quantidade(quantidade)
                .build();
    }

    @Override
    public ItemInventarioDTO dominioDto(ItemInventario dominio) {
        ItemInventarioDTO dto = new ItemInventarioDTO();
        dto.id = dominio.getId();
        dto.item = dominio.getItem();
        dto.pontos = dominio.getPontos();
        dto.quantidade = dominio.getQuantidade();
        return dto;
    }
}
