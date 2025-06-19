package com.infinityparfum.Pago.service;

import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;

    @InjectMocks private PagoService pagoService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Pago pago = new Pago(); pago.setMetodo("Tarjeta");
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<Pago> lista = pagoService.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Tarjeta", lista.get(0).getMetodo());
        verify(pagoRepository, times(1)).findAll();
    }
}
