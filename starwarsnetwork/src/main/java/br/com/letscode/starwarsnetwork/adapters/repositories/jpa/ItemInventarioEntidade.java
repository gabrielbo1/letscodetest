package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import br.com.letscode.starwarsnetwork.core.domain.Item;
import br.com.letscode.starwarsnetwork.core.domain.ItemInventario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "item_inventario")
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioEntidade implements Serializable, ParserEntidade<ItemInventario> {

    @Id
    @SequenceGenerator(name = "cod_inventario", sequenceName = "item_inventario_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_inventario")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rebelde_id", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private RebeldeEntidade rebeldeEntidade;

    @Column(name = "codigo_item")
    @Convert(converter = ItemConverter.class)
    private Item item;

    @Column(name = "pontos")
    private Integer pontos;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Override
    public ItemInventario entidadeDominio() {
        return ItemInventario.builder()
                .id(id)
                .item(item)
                .pontos(pontos)
                .quantidade(quantidade)
                .build();
    }

    @Override
    public ItemInventarioEntidade dominioEntidade(ItemInventario dominio) {
        return ItemInventarioEntidade.builder()
                .id(dominio.getId())
                .item(dominio.getItem())
                .pontos(dominio.getPontos())
                .quantidade(dominio.getQuantidade())
                .build();
    }
}
