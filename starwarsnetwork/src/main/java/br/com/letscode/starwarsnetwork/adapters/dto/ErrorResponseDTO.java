package br.com.letscode.starwarsnetwork.adapters.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private LocalDateTime data;
    private String mensagem;
}
