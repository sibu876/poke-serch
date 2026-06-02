package com.pokedex.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PokemonSummaryDto {
    private int id;
    private String name;
    private String imageUrl;
    private List<String> types;
}
