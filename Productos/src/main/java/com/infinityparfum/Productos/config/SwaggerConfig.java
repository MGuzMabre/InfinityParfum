package com.infinityparfum.Productos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API - Productos")
                .version("1.0")
                .description("Microservicio responsable del manejo del catálogo de productos. " +
                             "Permite realizar operaciones de creación, modificación, eliminación, " +
                             "visualización y control de stock de productos."));
    }
}
