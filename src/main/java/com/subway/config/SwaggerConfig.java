package com.subway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI(@Value("${springdoc.version}") String springdocVersion) {
        return new OpenAPI()
                .components(new Components())
                .servers(
                        List.of(
                                new Server().url("https://api.sub-recipe.com").description("server"),
                                new Server().url("http://localhost:8080").description("local")
                        )
                )
                .info(new Info().title("my API")
                        .description("sub-recipe API 명세서입니다.")
                        .version(springdocVersion));
    }
}
