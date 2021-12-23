package br.com.letscode.starwarsnetwork.core.domain;

/**
 * @author gabriel
 */
public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }
}
