package com.infinityparfum.Productos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ProductosApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se cargue correctamente
    }
}
