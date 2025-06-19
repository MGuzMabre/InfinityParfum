package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnvioServiceTest {

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
        Envio envio = new Envio();
        envio.setEstado("Pendiente");
        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> lista = envioService.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Pendiente", lista.get(0).getEstado());
        verify(envioRepository, times(1)).findAll();
    }
}
