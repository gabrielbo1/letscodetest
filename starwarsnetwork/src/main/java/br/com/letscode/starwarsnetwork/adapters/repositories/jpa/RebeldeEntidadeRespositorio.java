package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeEntidadeRespositorio extends JpaRepository<RebeldeEntidade, Long> {

    @Query(value = "SELECT COUNT(*) FROM REBELDE", nativeQuery = true)
    Long totalRebeldes();

    @Query(value = "SELECT COUNT(*) FROM REBELDE WHERE TRAIDOR = TRUE", nativeQuery = true)
    Long totalRebeldesTraidores();

    @Query(value = "SELECT SUM(QUANTIDADE) FROM ITEM_INVENTARIO  INNER JOIN REBELDE " +
            "ON (ITEM_INVENTARIO.REBELDE_ID = REBELDE.ID AND REBELDE.TRAIDOR = FALSE) WHERE CODIGO_ITEM = ?1", nativeQuery = true)
    Long quantidadeItens(Integer codigoItem);

    @Query(value = "SELECT count(*) FROM ITEM_INVENTARIO INNER JOIN REBELDE " +
            "ON (ITEM_INVENTARIO.REBELDE_ID = REBELDE.ID AND REBELDE.TRAIDOR = FALSE) WHERE CODIGO_ITEM = ?1", nativeQuery = true)
    Long quantidadeTipoItem(Integer codigoItem);

    @Query(value = "SELECT SUM(ITEM_INVENTARIO.PONTOS) FROM ITEM_INVENTARIO " +
            "INNER JOIN REBELDE ON ITEM_INVENTARIO.REBELDE_ID = REBELDE.ID " +
            "AND REBELDE.TRAIDOR = TRUE", nativeQuery = true)
    Long quantidadePontosPerdidosTraidores();
}
