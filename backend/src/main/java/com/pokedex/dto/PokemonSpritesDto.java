package com.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpritesDto {
    @JsonProperty("front_default")
    private String frontDefault;

    @JsonProperty("back_default")
    private String backDefault;

    @JsonProperty("front_shiny")
    private String frontShiny;

    @JsonProperty("back_shiny")
    private String backShiny;

    @JsonProperty("front_female")
    private String frontFemale;

    @JsonProperty("front_shiny_female")
    private String frontShinyFemale;

    private Other other;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Other {
        @JsonProperty("official-artwork")
        private OfficialArtwork officialArtwork;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class OfficialArtwork {
            @JsonProperty("front_default")
            private String frontDefault;

            @JsonProperty("front_shiny")
            private String frontShiny;
        }
    }
}
