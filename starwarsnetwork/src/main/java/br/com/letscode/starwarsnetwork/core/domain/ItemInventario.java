package br.com.letscode.starwarsnetwork.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ItemInventario {
    private Long id;
    private Item item;
    private Integer pontos;
    private Integer quantidade;

    public void valida() {
        if (Objects.isNull(item)) {
            throw new ErrorException("É necssário um item para cadastrar inventário!");
        }

        if (Objects.isNull(quantidade) || quantidade < 0) {
            throw new ErrorException("É necssário ao menos 1 item para cadastro!");
        }
        this.pontos = quantidade * item.getPontos();
    }

    public void atualizaQuantidadeItem(Integer q) {
        this.quantidade = q;
        this.pontos = quantidade * item.getPontos();
    }
}
