package br.com.letscode.starwarsnetwork.adapters.http;

import br.com.letscode.starwarsnetwork.adapters.dto.ErrorResponseDTO;
import br.com.letscode.starwarsnetwork.core.domain.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<Object> handleExceptions(ErrorException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setData(LocalDateTime.now());
        errorResponseDTO.setMensagem(exception.getMessage());
        ResponseEntity<Object> response = new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        return response;
    }
}
