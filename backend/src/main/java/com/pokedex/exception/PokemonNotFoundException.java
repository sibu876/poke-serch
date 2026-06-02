package com.pokedex.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String nameOrId) {
        super("Pokémon not found: " + nameOrId);
    }
}
