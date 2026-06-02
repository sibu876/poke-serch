package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonTypeDto {
    private int slot;
    private TypeInfo type;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeInfo {
        private String name;
        private String url;
    }
}
