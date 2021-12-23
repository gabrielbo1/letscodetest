package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import br.com.letscode.starwarsnetwork.core.domain.Genero;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class GeneroConverter implements AttributeConverter<Genero, String> {

    @Override
    public String convertToDatabaseColumn(Genero genero) {
        if (genero == null)
            return null;
        return genero.getCodigo().toString();
    }

    @Override
    public Genero convertToEntityAttribute(String codigo) {
        Optional<Genero> genero = Genero.buscarGeneroCodigo(Integer.parseInt(codigo));
        if (genero.isEmpty())
            throw new IllegalArgumentException("Valor inválido para escolha de genero");
        return genero.get();
    }
}
