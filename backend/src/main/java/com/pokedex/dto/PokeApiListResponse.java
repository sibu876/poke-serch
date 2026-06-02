package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiListResponse {
    private int count;
    private String next;
    private String previous;
    private List<PokemonRef> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PokemonRef {
        private String name;
        private String url;
    }
}
