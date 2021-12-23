package br.com.letscode.starwarsnetwork.core.usecases;

import br.com.letscode.starwarsnetwork.core.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RebeldeCaseTest {

    @Autowired
    private RebeldeCase rebeldeCase;
    
    final static Long ZERO = 0l;
    final static Long UM = 1l;
    final static Long DOIS = 2l;

    @Test
    void buscarRebeldeComId() {
        assertEquals(Optional.empty(), rebeldeCase.buscarRebeldeComId(ZERO));

        ErrorException errorException;
        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.buscarRebeldeComId(null));
        assertEquals(errorException.getMessage(), "Id nulo inválido para busca.");

        rebeldeCase.adicionarRebelde(rebeldeMock());
        assertTrue(rebeldeCase.buscarRebeldeComId(ZERO).isPresent());
    }

    @Test
    void listarTodosRebeldes() {
        assertTrue(rebeldeCase.listarTodosRebeldes().isEmpty());
        rebeldeCase.adicionarRebelde(rebeldeMock());
        assertFalse(rebeldeCase.listarTodosRebeldes().isEmpty());
    }

    @Test
    void adicionarRebelde() {
        rebeldeCase.adicionarRebelde(rebeldeMock());
        Optional<Rebelde> registro = rebeldeCase.buscarRebeldeComId(ZERO);
        assertTrue(registro.isPresent());
    }

    @Test
    void atualizarLocalizacaoRebelde() {
        rebeldeCase.adicionarRebelde(rebeldeMock());
        Rebelde atualizacao = Rebelde
                .builder()
                .id(ZERO)
                .nomeGalaxia("Outra Galáxia")
                .latitude(BigDecimal.valueOf(20))
                .longitude(BigDecimal.valueOf(30))
                .build();

        rebeldeCase.atualizarLocalizacaoRebelde(atualizacao);

        Optional<Rebelde> registro = rebeldeCase.buscarRebeldeComId(ZERO);
        assertTrue(registro.isPresent());
        assertEquals("Outra Galáxia", registro.get().getNomeGalaxia());
        assertEquals(BigDecimal.valueOf(20.0), registro.get().getLatitude());
        assertEquals(BigDecimal.valueOf(30.0), registro.get().getLongitude());
    }

    @Test
    void reportarRebelde1() {
        ErrorException errorException;
        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.reportarRebelde(ZERO, UM));
        assertEquals(errorException.getMessage(), "Registro do rebelde delator não encontrado.");

        rebeldeCase.adicionarRebelde(rebeldeMock());
        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.reportarRebelde(ZERO, UM));
        assertEquals(errorException.getMessage(), "Registro do rebelde traidor não encontrado.");
    }

    @Test
    void reportarRebeldeMinimoVotos() {
        int numeroVotos = 3;
        for (int i = 0; i < (numeroVotos + 1); i++) {
            rebeldeCase.adicionarRebelde(rebeldeMock());
        }

        for (int i = 1; i <= numeroVotos; i++) {
            rebeldeCase.reportarRebelde((long) i, ZERO);
        }

        Optional<Rebelde> registro = rebeldeCase.buscarRebeldeComId(ZERO);
        assertTrue(registro.isPresent());
        assertTrue(registro.get().getTraidor());
    }

    @Test
    void negociarItens() {
        ErrorException errorException;
        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), null, rebeldeMock(), null));
        assertEquals(errorException.getMessage(), "Lista de itens nulas ou vazias inválidas para negociação!");


        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), new ArrayList<>(), rebeldeMock(), null));
        assertEquals(errorException.getMessage(), "Lista de itens nulas ou vazias inválidas para negociação!");

        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), rebeldeMock().getItens(), rebeldeMock(), null));
        assertEquals(errorException.getMessage(), "Lista de itens nulas ou vazias inválidas para negociação!");

        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), rebeldeMock().getItens(), rebeldeMock(), new ArrayList<>()));
        assertEquals(errorException.getMessage(), "Lista de itens nulas ou vazias inválidas para negociação!");

        List<ItemInventario> listaItem =  new ArrayList<>();
        listaItem.add(ItemInventario.builder().quantidade(10).item(Item.ARMA).build());

        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), listaItem, rebeldeMock(), rebeldeMock().getItens()));
        assertEquals(errorException.getMessage(), "A soma dos itens deve ser a mesma para haver negociação!");

        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                rebeldeMock(), rebeldeMock().getItens(), rebeldeMock(), listaItem));
        assertEquals(errorException.getMessage(), "A soma dos itens deve ser a mesma para haver negociação!");

        reportarRebeldeMinimoVotos();
        Optional<Rebelde> registro = rebeldeCase.buscarRebeldeComId(ZERO);

        errorException = assertThrows(ErrorException.class, () -> {
            rebeldeCase.negociarItens(registro.get(), listaItem, rebeldeMock(), listaItem);
        });
        assertEquals(errorException.getMessage(), "Rebelde Nome é tido como traidor.");

        Optional<Rebelde> registroItemNaoListado = rebeldeCase.buscarRebeldeComId(UM);
        Optional<Rebelde> registroOutroNegociador = rebeldeCase.buscarRebeldeComId(DOIS);
        errorException = assertThrows(ErrorException.class, () -> rebeldeCase.negociarItens(
                registroItemNaoListado.get(), listaItem, registroOutroNegociador.get(), listaItem));
        assertEquals(errorException.getMessage(), "Item do tipo Arma com quantidade 10 não encontrado ou " +
                "com quantidade inferior ou não existe, negociador Nome.");
    }

    @Test
    void negociarItensStiuacaItensDiferentes() {
        rebeldeCase.adicionarRebelde(rebeldeMock());
        rebeldeCase.adicionarRebelde(Rebelde.builder()
                .id(ZERO)
                .nome("Nome 1")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia("Via Lactea")
                .itens(List.of(ItemInventario.builder().quantidade(5).item(Item.ARMA).build()))
                .build());

        Optional<Rebelde> primeiroNegociador = rebeldeCase.buscarRebeldeComId(ZERO);
        Optional<Rebelde> segundoNegociador  = rebeldeCase.buscarRebeldeComId(UM);
        rebeldeCase.negociarItens(primeiroNegociador.get(),
                                  primeiroNegociador.get().getItens(),
                                  segundoNegociador.get(),
                                  segundoNegociador.get().getItens());

        primeiroNegociador = rebeldeCase.buscarRebeldeComId(ZERO);
        segundoNegociador  = rebeldeCase.buscarRebeldeComId(UM);

        assertTrue(primeiroNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.AGUA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(0));

        assertTrue(primeiroNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.ARMA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(5));

        assertTrue(segundoNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.AGUA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(10));

        assertTrue(segundoNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.ARMA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(0));

        primeiroNegociador = rebeldeCase.buscarRebeldeComId(ZERO);
        segundoNegociador  = rebeldeCase.buscarRebeldeComId(UM);
        rebeldeCase.negociarItens(primeiroNegociador.get(),
                primeiroNegociador.get().getItens(),
                segundoNegociador.get(),
                segundoNegociador.get().getItens());

        primeiroNegociador = rebeldeCase.buscarRebeldeComId(ZERO);
        segundoNegociador  = rebeldeCase.buscarRebeldeComId(UM);

        assertTrue(segundoNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.AGUA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(0));

        assertTrue(segundoNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.ARMA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(5));

        assertTrue(primeiroNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.AGUA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(10));

        assertTrue(primeiroNegociador.get()
                .getItens()
                .stream()
                .filter(k -> k.getItem().equals(Item.ARMA))
                .collect(Collectors.toList())
                .get(0)
                .getQuantidade()
                .equals(0));

    }

    public static Rebelde rebeldeMock() {
        return Rebelde.builder()
                .id(ZERO)
                .nome("Nome")
                .idade(19)
                .genero(Genero.MASCULINO)
                .latitude(BigDecimal.valueOf(10))
                .longitude(BigDecimal.valueOf(10))
                .nomeGalaxia("Via Lactea")
                .itens(List.of(ItemInventario.builder().quantidade(10).item(Item.AGUA).build()))
                .build();
    }
}