package com.pokedex.service;

import com.pokedex.dto.PokemonResponseDto;
import com.pokedex.dto.PokemonSummaryDto;

import java.util.List;

public interface PokemonService {

    PokemonResponseDto getPokemonByNameOrId(String nameOrId);

    List<PokemonSummaryDto> searchPokemon(String query, int limit);

    List<PokemonSummaryDto> getPopularPokemon(int limit);
}
