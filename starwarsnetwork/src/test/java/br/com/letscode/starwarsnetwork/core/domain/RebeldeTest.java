package br.com.letscode.starwarsnetwork.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RebeldeTest {

    @Test
    public void valida() {
        ErrorException errorException;

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder().build().valida());

        assertEquals(errorException.getMessage(), "Nome do rebelde vazio ou superior a 150 caracteres, " +
                "inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder().nome("").build().valida());

        assertEquals(errorException.getMessage(), "Nome do rebelde vazio ou superior a 150 caracteres, " +
                "inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome(new String(new char[Rebelde.NOME_TAMANHO_MAX + 1])
                        .replace("\0", "a"))
                .build()
                .valida()
        );

        assertEquals(errorException.getMessage(), "Nome do rebelde vazio ou superior a 150 caracteres, " +
                "inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .build()
                .valida()
        );

        assertEquals(errorException.getMessage(), "Idade do rebelde nula ou superior a 150 anos, " +
                "inválida para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(Rebelde.IDADE_MAX + 1)
                .build().valida());

        assertEquals(errorException.getMessage(), "Idade do rebelde nula ou superior a 150 anos, " +
                "inválida para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .build().valida());

        assertEquals(errorException.getMessage(), "Genero nulo, inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .build().valida());

        assertEquals(errorException.getMessage(), "Latitude nula ou inválida, latitude máxima 90.0000000 mínima -90.0000000!");


        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(Rebelde.LATITUDE_MAX.add(BigDecimal.ONE))
                .build().valida());

        assertEquals(errorException.getMessage(), "Latitude nula ou inválida, latitude máxima 90.0000000 mínima -90.0000000!");


        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(Rebelde.LATITUDE_MIN.subtract(BigDecimal.ONE))
                .build().valida());

        assertEquals(errorException.getMessage(), "Latitude nula ou inválida, latitude máxima 90.0000000 mínima -90.0000000!");


        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .build().valida());

        assertEquals(errorException.getMessage(), "Longitude nula ou inválida, latitude máxima 180.0000000 mínima -180.0000000!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(Rebelde.LONGITUDE_MAX.add(BigDecimal.ONE))
                .build().valida());

        assertEquals(errorException.getMessage(), "Longitude nula ou inválida, latitude máxima 180.0000000 mínima -180.0000000!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(Rebelde.LONGITUDE_MIN.subtract(BigDecimal.ONE))
                .build().valida());

        assertEquals(errorException.getMessage(), "Longitude nula ou inválida, latitude máxima 180.0000000 mínima -180.0000000!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .build().valida());

        assertEquals(errorException.getMessage(), "Nome da galáxia vazio ou superior a 150 caracteres, inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia("")
                .build().valida());

        assertEquals(errorException.getMessage(), "Nome da galáxia vazio ou superior a 150 caracteres, inválido para cadastro!");

        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia(new String(new char[Rebelde.NOME_TAMANHO_MAX + 1])
                        .replace("\0", "a"))
                .build().valida());

        assertEquals(errorException.getMessage(), "Nome da galáxia vazio ou superior a 150 caracteres, inválido para cadastro!");


        errorException = assertThrows(ErrorException.class, () -> Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia("Via Lactea")
                .build().valida());

        assertEquals(errorException.getMessage(), "Adicione ao menos 1 um item para cadastrar um rebelde!");

        Rebelde.builder()
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia("Via Lactea")
                .itens(List.of(ItemInventario.builder().quantidade(10).item(Item.AGUA).build()))
                .build().valida();
    }
}