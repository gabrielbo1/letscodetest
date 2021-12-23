package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraidorEntidadeRepositorio extends JpaRepository<TraidorEntidade, Long> {

    @Query(value = "select * from traidor where rebelde_traidor_id = ?1", nativeQuery = true)
    List<TraidorEntidade> findByIdTraidor(Long idTraidor);
}
