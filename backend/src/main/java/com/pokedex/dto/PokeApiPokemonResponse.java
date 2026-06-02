package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiPokemonResponse {
    private int id;
    private String name;

    @JsonProperty("base_experience")
    private Integer baseExperience;

    private int height;
    private int weight;

    @JsonProperty("is_default")
    private boolean isDefault;

    private int order;

    private List<PokemonTypeDto> types;
    private List<PokemonStatDto> stats;
    private List<PokemonAbilityDto> abilities;
    private List<PokemonMoveDto> moves;
    private PokemonSpritesDto sprites;

    private SpeciesRef species;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SpeciesRef {
        private String name;
        private String url;
    }
}
