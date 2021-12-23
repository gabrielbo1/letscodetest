package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import br.com.letscode.starwarsnetwork.core.domain.Item;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class ItemConverter implements AttributeConverter<Item, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Item item) {
        return item.getCodigo();
    }

    @Override
    public Item convertToEntityAttribute(Integer s) {
        Optional<Item> item = Item.buscaItemCodigo(s);
        if (item.isEmpty())
            throw new IllegalArgumentException("Valor inválido para escolha de item.");
        return item.get();
    }
}
