package com.infinityparfum.Pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PedidosApplicationTests {

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se cargue correctamente
    }
}
