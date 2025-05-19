package com.infinityparfum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "com.infinityparfum.Envio.model",
    "com.infinityparfum.Pago.model",
    "com.infinityparfum.Pedidos.model",
    "com.infinityparfum.Productos.model",
    "com.infinityparfum.Usuario.model"
})
@EnableJpaRepositories(basePackages = {
    "com.infinityparfum.Envio.repository",
    "com.infinityparfum.Pago.repository",
    "com.infinityparfum.Pedidos.repository",
    "com.infinityparfum.Productos.repository",
    "com.infinityparfum.Usuario.repository"
})
public class InfinityParfumApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfinityParfumApplication.class, args);
    }

}