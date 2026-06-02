package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonMoveDto {
    private MoveInfo move;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MoveInfo {
        private String name;
        private String url;
    }
}
