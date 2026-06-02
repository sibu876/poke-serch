package com.pokedex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${pokeapi.base-url:https://pokeapi.co/api/v2}")
    private String pokeApiBaseUrl;

    @Bean
    public WebClient pokeApiWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .baseUrl(pokeApiBaseUrl)
                .exchangeStrategies(strategies)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
