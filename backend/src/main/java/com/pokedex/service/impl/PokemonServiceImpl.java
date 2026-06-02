package com.pokedex.service.impl;

import com.pokedex.config.CacheConfig;
import com.pokedex.dto.*;
import com.pokedex.exception.PokeApiException;
import com.pokedex.exception.PokemonNotFoundException;
import com.pokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final WebClient pokeApiWebClient;

    @Override
    @Cacheable(value = CacheConfig.POKEMON_CACHE, key = "#nameOrId.toLowerCase()")
    public PokemonResponseDto getPokemonByNameOrId(String nameOrId) {
        if (nameOrId == null || nameOrId.isBlank()) {
            throw new IllegalArgumentException("Pokémon name or ID must not be empty");
        }

        String normalized = nameOrId.trim().toLowerCase(Locale.ROOT);
        log.debug("Fetching Pokémon from PokeAPI: {}", normalized);

        PokeApiPokemonResponse raw = fetchFromPokeApi(normalized);
        return mapToResponseDto(raw);
    }

    @Override
    @Cacheable(value = CacheConfig.POKEMON_LIST_CACHE, key = "#query.toLowerCase() + '_' + #limit")
    public List<PokemonSummaryDto> searchPokemon(String query, int limit) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query must not be empty");
        }
        if (limit < 1 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }

        String normalized = query.trim().toLowerCase(Locale.ROOT);
        log.debug("Searching Pokémon with query: {}", normalized);

        PokeApiListResponse listResponse = fetchPokemonList(1500, 0);

        return listResponse.getResults().stream()
                .filter(ref -> ref.getName().contains(normalized))
                .limit(limit)
                .map(ref -> {
                    try {
                        return getSummary(ref.getName());
                    } catch (Exception e) {
                        log.warn("Failed to fetch summary for {}: {}", ref.getName(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CacheConfig.POKEMON_LIST_CACHE, key = "'popular_' + #limit")
    public List<PokemonSummaryDto> getPopularPokemon(int limit) {
        log.debug("Fetching popular Pokémon, limit={}", limit);
        PokeApiListResponse listResponse = fetchPokemonList(limit, 0);

        return listResponse.getResults().stream()
                .map(ref -> {
                    try {
                        return getSummary(ref.getName());
                    } catch (Exception e) {
                        log.warn("Failed to fetch summary for {}: {}", ref.getName(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PokemonSummaryDto getSummary(String name) {
        PokeApiPokemonResponse raw = fetchFromPokeApi(name);
        String imageUrl = resolveImageUrl(raw);
        List<String> types = raw.getTypes().stream()
                .map(t -> t.getType().getName())
                .collect(Collectors.toList());

        return PokemonSummaryDto.builder()
                .id(raw.getId())
                .name(raw.getName())
                .imageUrl(imageUrl)
                .types(types)
                .build();
    }

    private PokeApiPokemonResponse fetchFromPokeApi(String nameOrId) {
        try {
            return pokeApiWebClient.get()
                    .uri("/pokemon/{nameOrId}", nameOrId)
                    .retrieve()
                    .bodyToMono(PokeApiPokemonResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PokemonNotFoundException(nameOrId);
            }
            throw new PokeApiException(e.getMessage(), e.getStatusCode().value());
        } catch (Exception e) {
            log.error("Unexpected error fetching Pokémon {}: {}", nameOrId, e.getMessage());
            throw new PokeApiException("Failed to reach PokeAPI: " + e.getMessage(), 502);
        }
    }

    private PokeApiListResponse fetchPokemonList(int limit, int offset) {
        try {
            return pokeApiWebClient.get()
                    .uri(uri -> uri.path("/pokemon")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build())
                    .retrieve()
                    .bodyToMono(PokeApiListResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new PokeApiException("Failed to fetch Pokémon list", e.getStatusCode().value());
        }
    }

    private PokemonResponseDto mapToResponseDto(PokeApiPokemonResponse raw) {
        List<String> types = raw.getTypes().stream()
                .map(t -> t.getType().getName())
                .collect(Collectors.toList());

        List<PokemonResponseDto.StatResponse> stats = raw.getStats().stream()
                .map(s -> PokemonResponseDto.StatResponse.builder()
                        .name(s.getStat().getName())
                        .baseStat(s.getBaseStat())
                        .effort(s.getEffort())
                        .build())
                .collect(Collectors.toList());

        List<PokemonResponseDto.AbilityResponse> abilities = raw.getAbilities().stream()
                .map(a -> PokemonResponseDto.AbilityResponse.builder()
                        .name(a.getAbility().getName())
                        .hidden(a.isHidden())
                        .slot(a.getSlot())
                        .build())
                .collect(Collectors.toList());

        List<String> moves = raw.getMoves().stream()
                .map(m -> m.getMove().getName())
                .limit(20)
                .collect(Collectors.toList());

        PokemonSpritesDto sp = raw.getSprites();
        PokemonResponseDto.SpritesResponse sprites = PokemonResponseDto.SpritesResponse.builder()
                .frontDefault(sp.getFrontDefault())
                .backDefault(sp.getBackDefault())
                .frontShiny(sp.getFrontShiny())
                .backShiny(sp.getBackShiny())
                .officialArtwork(sp.getOther() != null && sp.getOther().getOfficialArtwork() != null
                        ? sp.getOther().getOfficialArtwork().getFrontDefault() : null)
                .officialArtworkShiny(sp.getOther() != null && sp.getOther().getOfficialArtwork() != null
                        ? sp.getOther().getOfficialArtwork().getFrontShiny() : null)
                .build();

        return PokemonResponseDto.builder()
                .id(raw.getId())
                .name(raw.getName())
                .height(raw.getHeight())
                .weight(raw.getWeight())
                .baseExperience(raw.getBaseExperience())
                .imageUrl(resolveImageUrl(raw))
                .shinyImageUrl(sp.getFrontShiny())
                .types(types)
                .stats(stats)
                .abilities(abilities)
                .moves(moves)
                .sprites(sprites)
                .build();
    }

    private String resolveImageUrl(PokeApiPokemonResponse raw) {
        if (raw.getSprites() != null
                && raw.getSprites().getOther() != null
                && raw.getSprites().getOther().getOfficialArtwork() != null
                && raw.getSprites().getOther().getOfficialArtwork().getFrontDefault() != null) {
            return raw.getSprites().getOther().getOfficialArtwork().getFrontDefault();
        }
        if (raw.getSprites() != null && raw.getSprites().getFrontDefault() != null) {
            return raw.getSprites().getFrontDefault();
        }
        return null;
    }
}
