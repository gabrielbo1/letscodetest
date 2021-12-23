package br.com.letscode.starwarsnetwork.adapters.dto;

import br.com.letscode.starwarsnetwork.adapters.repositories.jpa.ParserEntidade;

public interface ParserDTO<T> {
    T dtoDominio();
    ParserDTO<T> dominioDto(T dominio);
}
