package com.infinityparfum.Envio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test") // Usa perfil test
class EnvioApplicationTests {

    @MockitoBean
    private RestTemplate restTemplate; // Simula RestTemplate

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se cargue sin errores
    }
}
