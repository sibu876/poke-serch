package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonAbilityDto {
    @JsonProperty("is_hidden")
    private boolean isHidden;
    private int slot;
    private AbilityInfo ability;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AbilityInfo {
        private String name;
        private String url;
    }
}
