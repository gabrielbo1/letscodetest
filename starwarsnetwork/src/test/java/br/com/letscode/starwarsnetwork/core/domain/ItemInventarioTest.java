package br.com.letscode.starwarsnetwork.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemInventarioTest {

    @Test
    void valida() {
        ErrorException errorException;

        errorException = assertThrows(ErrorException.class, () -> ItemInventario.builder().build().valida());

        assertEquals(errorException.getMessage(), "É necssário um item para cadastrar inventário!");

        errorException = assertThrows(ErrorException.class, () -> ItemInventario.builder()
                .item(Item.AGUA)
                .build()
                .valida());

        assertEquals(errorException.getMessage(), "É necssário ao menos 1 item para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> ItemInventario.builder()
                .item(Item.AGUA)
                .quantidade(-1)
                .build()
                .valida());

        assertEquals(errorException.getMessage(), "É necssário ao menos 1 item para cadastro!");

        ItemInventario.builder()
                .item(Item.AGUA)
                .quantidade(10)
                .build()
                .valida();
    }
}