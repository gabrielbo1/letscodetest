package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioEntidadeRepositorio extends JpaRepository<ItemInventarioEntidade, Long> {
}
