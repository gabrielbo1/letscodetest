package br.com.letscode.starwarsnetwork.adapters.repositories.jpa;

import br.com.letscode.starwarsnetwork.core.domain.Genero;
import br.com.letscode.starwarsnetwork.core.domain.Rebelde;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@Table(name = "rebelde")
@NoArgsConstructor
@AllArgsConstructor
public class RebeldeEntidade implements Serializable, ParserEntidade<Rebelde> {

    @Id
    @SequenceGenerator(name = "cod_rebelde", sequenceName = "rebelde_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_rebelde")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "genero")
    @Convert(converter = GeneroConverter.class)
    private Genero genero;

    @Column(name = "latitude", precision = 2, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 3, scale = 7)
    private BigDecimal longitude;

    @Column(name = "nome_galaxia", length = 150)
    private String nomeGalaxia;

    @Column(name = "traidor")
    private Boolean traidor;

    @OneToMany(mappedBy = "rebeldeEntidade", orphanRemoval = true,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemInventarioEntidade> itens;

    @Override
    public Rebelde entidadeDominio() {
        return Rebelde.builder()
                .id(id)
                .nome(nome)
                .idade(idade)
                .genero(genero)
                .latitude(latitude)
                .longitude(longitude)
                .nomeGalaxia(nomeGalaxia)
                .traidor(traidor)
                .itens(itens.stream()
                        .map(ItemInventarioEntidade::entidadeDominio)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public RebeldeEntidade dominioEntidade(Rebelde dominio) {
        RebeldeEntidade rebeldeEntidade = RebeldeEntidade.builder()
                .id(dominio.getId())
                .nome(dominio.getNome())
                .idade(dominio.getIdade())
                .genero(dominio.getGenero())
                .latitude(dominio.getLatitude())
                .longitude(dominio.getLongitude())
                .traidor(Objects.isNull(dominio.getTraidor()) ? false : dominio.getTraidor())
                .nomeGalaxia(dominio.getNomeGalaxia())
                .itens(dominio.getItens()
                        .stream()
                        .map(ItemInventarioEntidade.builder().build()::dominioEntidade)
                        .collect(Collectors.toList()))
                .build();
        rebeldeEntidade.getItens().forEach(k -> k.setRebeldeEntidade(rebeldeEntidade));
        return rebeldeEntidade;
    }
}
