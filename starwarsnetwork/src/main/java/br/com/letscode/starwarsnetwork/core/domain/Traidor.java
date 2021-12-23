package br.com.letscode.starwarsnetwork.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class Traidor {
    private Long id;
    private Rebelde delator;
    private Rebelde traidor;

    public Traidor(Rebelde delator, Rebelde traidor) {
        if (Objects.isNull(delator) || delator.getTraidor()) {
            throw new ErrorException("Delator nulo ou marcado como traidor, inválido!");
        }

        if (delator.getTraidor()) {
            throw new ErrorException("Rebelde que deseja realizar a delação é tido como traidor.");
        }

        if (Objects.isNull(traidor)) {
            throw new ErrorException("É necssário um rebelde, para apondar como traidor!");
        }
        this.delator = delator;
        this.traidor = traidor;
    }
}
