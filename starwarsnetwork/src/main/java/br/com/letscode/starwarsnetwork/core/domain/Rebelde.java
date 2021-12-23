package br.com.letscode.starwarsnetwork.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author gabriel
 */
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class Rebelde {
    private Long id;
    private String nome;
    private Integer idade;
    private Genero genero;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String nomeGalaxia;
    private Boolean traidor;
    private List<ItemInventario> itens;

    public static short NOME_TAMANHO_MAX = 150;
    public static short IDADE_MAX = 150;
    public static BigDecimal LATITUDE_MAX = new BigDecimal("90.0000000");
    public static BigDecimal LATITUDE_MIN = new BigDecimal("-90.0000000");
    public static BigDecimal LONGITUDE_MAX = new BigDecimal("180.0000000");
    public static BigDecimal LONGITUDE_MIN = new BigDecimal("-180.0000000");

    public void valida() {
        if (Objects.isNull(nome) || nome.isBlank() || nome.length() > NOME_TAMANHO_MAX) {
            throw new ErrorException("Nome do rebelde vazio ou superior a 150 caracteres, inválido para cadastro!");
        }

        if (Objects.isNull(idade) || idade > IDADE_MAX) {
            throw new ErrorException("Idade do rebelde nula ou superior a 150 anos, inválida para cadastro!");
        }

        if (Objects.isNull(genero)) {
            throw new ErrorException("Genero nulo, inválido para cadastro!");
        }

        if (Objects.isNull(latitude) || latitude.compareTo(LATITUDE_MIN) < 0
                || latitude.compareTo(LATITUDE_MAX) > 0) {
            throw new ErrorException("Latitude nula ou inválida, latitude máxima "
                    + LATITUDE_MAX.toString() + " mínima " + LATITUDE_MIN.toString() + "!");
        }

        if (Objects.isNull(longitude) || longitude.compareTo(LONGITUDE_MIN) < 0
                || longitude.compareTo(LONGITUDE_MAX) > 0) {
            throw new ErrorException("Longitude nula ou inválida, latitude máxima "
                    + LONGITUDE_MAX.toString() + " mínima " + LONGITUDE_MIN.toString() + "!");
        }

        if (Objects.isNull(nomeGalaxia) || nomeGalaxia.isBlank() || nomeGalaxia.length() > NOME_TAMANHO_MAX) {
            throw new ErrorException("Nome da galáxia vazio ou superior a 150 caracteres, inválido para cadastro!");
        }

        if (Objects.isNull(itens) || itens.isEmpty()) {
            throw new ErrorException("Adicione ao menos 1 um item para cadastrar um rebelde!");
        }

        itens.forEach(ItemInventario::valida);
    }
}
