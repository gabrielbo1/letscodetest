package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import br.com.letscode.starwarsnetwork.core.domain.Traidor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "traidor")
@NoArgsConstructor
@AllArgsConstructor
public class TraidorEntidade implements Serializable, ParserEntidade<Traidor> {

    @Id
    @SequenceGenerator(name = "cod_traidor", sequenceName = "traidor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_traidor")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rebelde_id", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private RebeldeEntidade delator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rebelde_traidor_id", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private RebeldeEntidade traidor;

    @Override
    public Traidor entidadeDominio() {
        return Traidor.builder()
                .id(id)
                .delator(delator.entidadeDominio())
                .traidor(traidor.entidadeDominio())
                .build();
    }

    @Override
    public TraidorEntidade dominioEntidade(Traidor dominio) {
        RebeldeEntidade parsing = new RebeldeEntidade();
        return TraidorEntidade.builder()
                .id(dominio.getId())
                .delator(parsing.dominioEntidade(dominio.getDelator()))
                .traidor(parsing.dominioEntidade(dominio.getTraidor()))
                .build();
    }
}
