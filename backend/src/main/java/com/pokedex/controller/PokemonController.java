package com.pokedex.controller;

import com.pokedex.dto.PokemonResponseDto;
import com.pokedex.dto.PokemonSummaryDto;
import com.pokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    /**
     * Get full Pokémon details by name or numeric ID.
     * GET /api/v1/pokemon/{nameOrId}
     */
    @GetMapping("/{nameOrId}")
    public ResponseEntity<PokemonResponseDto> getPokemon(@PathVariable String nameOrId) {
        log.info("GET /api/v1/pokemon/{}", nameOrId);
        PokemonResponseDto response = pokemonService.getPokemonByNameOrId(nameOrId);
        return ResponseEntity.ok(response);
    }

    /**
     * Search Pokémon by partial name.
     * GET /api/v1/pokemon/search?q=bulba&limit=10
     */
    @GetMapping("/search")
    public ResponseEntity<List<PokemonSummaryDto>> searchPokemon(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        log.info("GET /api/v1/pokemon/search?q={}&limit={}", query, limit);
        List<PokemonSummaryDto> results = pokemonService.searchPokemon(query, limit);
        return ResponseEntity.ok(results);
    }

    /**
     * Get a list of popular / first-generation Pokémon for the home screen.
     * GET /api/v1/pokemon?limit=20
     */
    @GetMapping
    public ResponseEntity<List<PokemonSummaryDto>> getPopularPokemon(
            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        log.info("GET /api/v1/pokemon?limit={}", limit);
        List<PokemonSummaryDto> results = pokemonService.getPopularPokemon(limit);
        return ResponseEntity.ok(results);
    }
}
