package br.com.letscode.starwarsnetwork.adapters.dto;

public interface ParserDTO<T> {
    T dtoDominio();
    ParserDTO<T> dominioDto(T dominio);
}
