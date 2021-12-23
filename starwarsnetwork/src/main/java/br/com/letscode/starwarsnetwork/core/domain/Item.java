package br.com.letscode.starwarsnetwork.core.domain;

import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum Item {
    ARMA(1, "Arma", 4),
    MUNICAO(2, "Munição", 3),
    AGUA(3, "Água", 2),
    COMIDA(4, "Comida", 1);

    private final int codigo;
    private final String nome;
    private final int pontos;

    Item(int codigo, String nome, int pontos) {
        this.codigo = codigo;
        this.nome = nome;
        this.pontos = pontos;
    }

    public static Optional<Item> buscaItemCodigo(int codigo) {
        return Stream.of(values()).filter(k -> k.getCodigo() == codigo).findFirst();
    }
}
