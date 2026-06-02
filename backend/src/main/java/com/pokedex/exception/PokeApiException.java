package com.pokedex.exception;

public class PokeApiException extends RuntimeException {
    private final int statusCode;

    public PokeApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
