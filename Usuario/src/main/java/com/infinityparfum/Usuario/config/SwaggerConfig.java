package com.infinityparfum.Usuario.config;

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
                .title("API - Usuario")
                .version("1.0")
                .description("Microservicio que se encarga de la gestión de usuarios del sistema, "
                + "incluyendo operaciones de registro, se asignan roles "
                +"validación de existencia y control de estado activo/inactivo."));
    }
}
