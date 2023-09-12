package com.subway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI springShopOpenAPI(@Value("${springdoc.version}") String springdocVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("my API")
                        .description("sub-recipe API 명세서입니다.")
                        .version(springdocVersion));
    }
}
