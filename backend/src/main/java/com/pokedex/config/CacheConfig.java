package com.pokedex.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String POKEMON_CACHE = "pokemon";
    public static final String POKEMON_LIST_CACHE = "pokemonList";

    private static final int MAX_CACHE_SIZE = 500;
    private static final long CACHE_TTL_MINUTES = 30;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(MAX_CACHE_SIZE)
                .expireAfterWrite(CACHE_TTL_MINUTES, TimeUnit.MINUTES)
                .recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(POKEMON_CACHE, POKEMON_LIST_CACHE);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
