package com.infinityparfum.Envio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EnvioApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnvioApplication.class, args);
    }

    @Bean(name = "envioRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}