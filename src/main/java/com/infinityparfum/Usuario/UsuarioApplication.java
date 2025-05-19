package com.infinityparfum.Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.infinityparfum.usuario.model"})
public class UsuarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsuarioApplication.class, args);
    }
}