package br.com.letscode.starwarsnetwork.core.usecases;

import br.com.letscode.starwarsnetwork.core.domain.ErrorException;
import br.com.letscode.starwarsnetwork.core.domain.ItemInventario;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import br.com.letscode.starwarsnetwork.core.domain.Traidor;
import br.com.letscode.starwarsnetwork.core.ports.RebeldeRepositorio;
import br.com.letscode.starwarsnetwork.core.ports.RebeldeUseCase;
import br.com.letscode.starwarsnetwork.core.ports.TraidorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RebeldeCase implements RebeldeUseCase {

    @Autowired
    private RebeldeRepositorio rebeldeRepositorio;

    @Autowired
    private TraidorRepositorio traidorRepositorio;

    @Override
    @Transactional
    public Optional<Rebelde> buscarRebeldeComId(Long id) {
        if (Objects.isNull(id)) {
            throw new ErrorException("Id nulo inválido para busca.");
        }
        return rebeldeRepositorio.buscarPorId(id);
    }

    @Override
    @Transactional
    public List<Rebelde> listarTodosRebeldes() {
        return rebeldeRepositorio.buscarTodosRebeldes();
    }

    @Override
    @Transactional
    public Rebelde adicionarRebelde(Rebelde rebelde) {
        rebelde.valida();
        return rebeldeRepositorio.salvar(rebelde);
    }

    @Override
    @Transactional
    public void atualizarLocalizacaoRebelde(Rebelde atualizacao) {
        Optional<Rebelde> registroRebelde = rebeldeRepositorio.buscarPorId(atualizacao.getId());
        if (registroRebelde.isPresent()) {
            Rebelde novoRegistro = Rebelde.builder()
                    .id(registroRebelde.get().getId())
                    .nome(registroRebelde.get().getNome())
                    .idade(registroRebelde.get().getIdade())
                    .genero(registroRebelde.get().getGenero())
                    .latitude(atualizacao.getLatitude())
                    .longitude(atualizacao.getLongitude())
                    .nomeGalaxia(atualizacao.getNomeGalaxia())
                    .traidor(registroRebelde.get().getTraidor())
                    .itens(registroRebelde.get().getItens())
                    .build();

            novoRegistro.valida();
            rebeldeRepositorio.atualizar(novoRegistro);
        }
    }

    @Override
    @Transactional
    public void reportarRebelde(Long idDelator, Long idTraidor) {
        Optional<Rebelde> registroDelator = rebeldeRepositorio.buscarPorId(idDelator);
        Optional<Rebelde> registroTraidor = rebeldeRepositorio.buscarPorId(idTraidor);

        if (!registroDelator.isPresent()) {
            throw new ErrorException("Registro do rebelde delator não encontrado.");
        }

        if (!registroTraidor.isPresent()) {
            throw new ErrorException("Registro do rebelde traidor não encontrado.");
        }

        Traidor traidor = new Traidor(registroDelator.get(), registroTraidor.get());
        traidorRepositorio.Salvar(traidor);

        if (!registroTraidor.get().getTraidor()) {
            List<Traidor> votos = traidorRepositorio.buscarListaVotos(registroTraidor.get());
            if (votos.stream()
                    .map(Traidor::getDelator)
                    .map(Rebelde::getId)
                    .distinct()
                    .collect(Collectors.toList()).size() >= 3) {
                Rebelde atualizacaoRegistro = Rebelde.builder()
                        .id(registroTraidor.get().getId())
                        .nome(registroTraidor.get().getNome())
                        .idade(registroTraidor.get().getIdade())
                        .genero(registroTraidor.get().getGenero())
                        .latitude(registroTraidor.get().getLatitude())
                        .longitude(registroTraidor.get().getLongitude())
                        .nomeGalaxia(registroTraidor.get().getNomeGalaxia())
                        .traidor(true)
                        .itens(registroTraidor.get().getItens())
                        .build();
                rebeldeRepositorio.atualizar(atualizacaoRegistro);
            }
        }
    }

    @Override
    @Transactional
    public void negociarItens(Rebelde primeiroNegociador, List<ItemInventario> primeiraLista,
                              Rebelde segundoNegociador, List<ItemInventario> segundaLista) {
        if (Objects.isNull(primeiraLista)
                || primeiraLista.isEmpty()
                || Objects.isNull(segundaLista)
                || segundaLista.isEmpty()) {
            throw new ErrorException("Lista de itens nulas ou vazias inválidas para negociação!");
        }

        if (somaPontosLista(primeiraLista) != somaPontosLista(segundaLista)) {
            throw new ErrorException("A soma dos itens deve ser a mesma para haver negociação!");
        }

        Optional<Rebelde> registroPrimeiroNegociador
                = rebeldeRepositorio.buscarPorId(primeiroNegociador.getId());
        Optional<Rebelde> registroSegundoNegociador
                = rebeldeRepositorio.buscarPorId(segundoNegociador.getId());

        checaRegistroRebelde(registroPrimeiroNegociador, primeiroNegociador);
        checaRegistroRebelde(registroSegundoNegociador, segundoNegociador);

        checaRebeldeTraidor(registroPrimeiroNegociador.get());
        checaRebeldeTraidor(registroSegundoNegociador.get());

        checaItensRegistroNegociador(registroPrimeiroNegociador.get(), primeiraLista);
        checaItensRegistroNegociador(registroSegundoNegociador.get(), segundaLista);

        atualizaItensRebelde(registroPrimeiroNegociador.get(), primeiraLista, segundaLista);
        atualizaItensRebelde(registroSegundoNegociador.get(), segundaLista, primeiraLista);

        rebeldeRepositorio.atualizar(registroPrimeiroNegociador.get());
        rebeldeRepositorio.atualizar(registroSegundoNegociador.get());
    }

    private int somaPontosLista(List<ItemInventario> listaItens) {
        return listaItens.stream()
                .mapToInt(k -> k.getItem().getPontos() * k.getQuantidade())
                .sum();
    }

    private void checaRegistroRebelde(Optional<Rebelde> registro, Rebelde rebelde) {
        if (!registro.isPresent()) {
            throw new ErrorException(String.format("Registro do rebelde %s não foi encontrado", rebelde.getNome()));
        }
    }

    private void checaRebeldeTraidor(Rebelde registro) {
        if (registro.getTraidor()) {
            throw new ErrorException(String.format("Rebelde %s é tido como traidor.", registro.getNome()));
        }
    }

    private void checaItensRegistroNegociador(Rebelde registro, List<ItemInventario> listaItens) {
        for (ItemInventario inventario : listaItens) {
            if (!registro.getItens()
                    .stream()
                    .anyMatch(k -> k.getItem().equals(inventario.getItem()) &&
                            k.getQuantidade() >= inventario.getQuantidade())) {
                throw new ErrorException(String.format("Item do tipo %s com quantidade %d " +
                                "não encontrado ou com quantidade inferior ou não existe, negociador %s.",
                        inventario.getItem().getNome(), inventario.getQuantidade(), registro.getNome()));
            }
        }
    }

    private void atualizaItensRebelde(Rebelde registro, List<ItemInventario> listaItensCedidos, List<ItemInventario> itensGanhos) {
        for (ItemInventario itemRegistr : registro.getItens()) {
            for (ItemInventario itemCedido : listaItensCedidos) {
                if (itemRegistr.getItem().equals(itemCedido.getItem())) {
                    itemRegistr.atualizaQuantidadeItem(itemRegistr.getQuantidade() - itemCedido.getQuantidade());
                }
            }
        }

        for (ItemInventario itemRegistr : registro.getItens()) {
            for (ItemInventario itemGanho : itensGanhos) {
                if (itemRegistr.getItem().equals(itemGanho.getItem())) {
                    itemRegistr.atualizaQuantidadeItem(itemRegistr.getQuantidade() + itemGanho.getQuantidade());
                }
            }
        }

        List<ItemInventario> novosItens = new ArrayList<>();
        for (ItemInventario novoItem : itensGanhos) {
            if (!registro.getItens().stream().filter(k -> k.getItem().equals(novoItem.getItem())).findAny().isPresent()) {
                novosItens.add(novoItem);
            }
        }

        registro.getItens()
                .addAll(novosItens.stream()
                        .map(k -> ItemInventario.builder().item(k.getItem()).quantidade(k.getQuantidade()).build())
                        .collect(Collectors.toList()));
        for (ItemInventario iten : registro.getItens()) {
            iten.valida();
        }
    }
}
