package com.infinityparfum.Pedidos.config;

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
                .title("API - Pedidos")
                .version("1.0")
                .description("Microservicio que administra los pedidos realizados por los clientes. " +
                             "Permite crear nuevos pedidos, asociar pagos y envíos, " +
                             "y consultar o eliminar registros existentes."));
    }
}
 