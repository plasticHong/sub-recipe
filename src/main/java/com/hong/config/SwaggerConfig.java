package com.hong.config;

import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .components(new Components())
//                .servers(
//                        List.of(
//                                new Server().url("https://api.sub-recipe.com").description("server"),
//                                new Server().url("http://localhost:8080").description("local")
//                        )
//                )
//                .info(new Info().title("my API")
//                        .description("sub-recipe API 명세서입니다.")
//                        .version(springdocVersion))
//                ;
//    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {

        return OpenApi -> OpenApi
                .addServersItem(new Server().url("https://api.sub-recipe.com").description("server"))
                .addServersItem(new Server().url("http://localhost:8080").description("local"));
    }

    @Bean
    public GroupedOpenApi subRecipeDocs() {

        return GroupedOpenApi.builder()
                .group("sub-recipe")
                .packagesToScan("com.hong.api.sub")
                .addOpenApiCustomizer(openApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi LimDocs() {
        return GroupedOpenApi.builder()
                .group("lim")
                .packagesToScan("com.hong.api.lim")
                .build();
    }

}
