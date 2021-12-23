package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

public interface ParserEntidade<T> {
    T entidadeDominio();
    ParserEntidade<T> dominioEntidade(T dominio);
}
