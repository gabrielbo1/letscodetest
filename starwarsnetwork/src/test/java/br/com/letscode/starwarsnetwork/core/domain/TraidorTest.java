package br.com.letscode.starwarsnetwork.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraidorTest {

    @Test
    void traidorTest() {
        ErrorException errorException;

        errorException = assertThrows(ErrorException.class, () -> new Traidor(null, null));
        assertEquals(errorException.getMessage(), "Delator nulo ou marcado como traidor, inválido!");

        errorException = assertThrows(ErrorException.class, () -> new Traidor(Rebelde.builder()
                .traidor(true)
                .build(), null));
        assertEquals(errorException.getMessage(), "Delator nulo ou marcado como traidor, inválido!");

        errorException = assertThrows(ErrorException.class, () -> new Traidor(Rebelde.builder()
                .traidor(false)
                .build(), null));
        assertEquals(errorException.getMessage(), "É necssário um rebelde, para apondar como traidor!");

        new Traidor(Rebelde.builder()
                .traidor(false).build(), Rebelde.builder().build());
    }

}