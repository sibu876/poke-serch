package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonStatDto {
    @JsonProperty("base_stat")
    private int baseStat;
    private int effort;
    private StatInfo stat;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatInfo {
        private String name;
        private String url;
    }
}
