package com.pokedex.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PokemonResponseDto {
    private int id;
    private String name;
    private int height;
    private int weight;
    private Integer baseExperience;
    private String imageUrl;
    private String shinyImageUrl;
    private List<String> types;
    private List<StatResponse> stats;
    private List<AbilityResponse> abilities;
    private List<String> moves;
    private SpritesResponse sprites;

    @Data
    @Builder
    public static class StatResponse {
        private String name;
        private int baseStat;
        private int effort;
    }

    @Data
    @Builder
    public static class AbilityResponse {
        private String name;
        private boolean hidden;
        private int slot;
    }

    @Data
    @Builder
    public static class SpritesResponse {
        private String frontDefault;
        private String backDefault;
        private String frontShiny;
        private String backShiny;
        private String officialArtwork;
        private String officialArtworkShiny;
    }
}
