package br.com.letscode.starwarsnetwork.core.domain;

import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author gabriel
 */
@Getter
public enum Genero {
    MASCULINO(1),
    FEMININO(2),
    NAO_INFORMADO(3);

    private final Integer codigo;

    Genero(int codigo) {
        this.codigo = codigo;
    }

    public static Optional<Genero> buscarGeneroCodigo(Integer codigo) {
        return Stream.of(values()).filter(k -> k.getCodigo() == codigo).findAny();
    }
}
