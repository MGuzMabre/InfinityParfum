package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        Envio envio1 = new Envio();
        envio1.setId(1L);
        envio1.setDireccion("Calle Falsa 123");
        envio1.setEstado("Pendiente");

        Envio envio2 = new Envio();
        envio2.setId(2L);
        envio2.setDireccion("Av. Siempre Viva 742");
        envio2.setEstado("En tránsito");

        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> resultado = envioService.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals("Pendiente", resultado.get(0).getEstado());
        assertEquals("En tránsito", resultado.get(1).getEstado());

        verify(envioRepository, times(1)).findAll();
    }
}
