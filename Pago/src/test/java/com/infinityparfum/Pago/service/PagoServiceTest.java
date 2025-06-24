package com.infinityparfum.Pago.service;

import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private PagoService pagoService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Pago pago = new Pago(); pago.setMetodo(new MetodoPago(1, "Tarjeta", null));
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<Pago> lista = pagoService.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Tarjeta", lista.get(0).getMetodo().getNombre());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    void testCrearPagoExitoso() {
        Pago pago = new Pago();
        pago.setPedidoId(10L);
        pago.setMetodo(new MetodoPago(1, "Tarjeta", null));
        pago.setDescripcion("desc");
        pago.setMonto(100.0);

        // Simula que el pedido existe
        when(restTemplate.getForObject(contains("/pedidos/10"), eq(Object.class))).thenReturn(new Object());
        when(pagoRepository.save(pago)).thenReturn(pago);

        Pago resultado = pagoService.crearPago(pago);

        assertEquals(pago, resultado);
        verify(restTemplate).getForObject(contains("/pedidos/10"), eq(Object.class));
        verify(pagoRepository).save(pago);
    }

    @Test
    void testCrearPagoPedidoNoExiste() {
        Pago pago = new Pago();
        pago.setPedidoId(99L);
        pago.setMetodo(new MetodoPago(1, "Tarjeta", null));
        pago.setDescripcion("desc");
        pago.setMonto(100.0);

        when(restTemplate.getForObject(contains("/pedidos/99"), eq(Object.class))).thenThrow(new RuntimeException("No existe"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> pagoService.crearPago(pago));
        assertTrue(ex.getMessage().contains("no existe"));
        verify(restTemplate).getForObject(contains("/pedidos/99"), eq(Object.class));
        verify(pagoRepository, never()).save(any());
    }

    @Test
    void testObtenerPorIdExiste() {
        Pago pago = new Pago();
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        Pago resultado = pagoService.obtenerPorId(1L);
        assertEquals(pago, resultado);
        verify(pagoRepository).findById(1L);
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> pagoService.obtenerPorId(1L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void testActualizarPago() {
        Pago pagoExistente = new Pago();
        pagoExistente.setDescripcion("vieja");
        pagoExistente.setMetodo(new MetodoPago(1, "Tarjeta", null));
        pagoExistente.setMonto(50.0);

        Pago datos = new Pago();
        datos.setDescripcion("nueva");
        datos.setMetodo(new MetodoPago(2, "Transferencia", null));
        datos.setMonto(200.0);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoExistente));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago resultado = pagoService.actualizarPago(1L, datos);

        assertEquals("nueva", resultado.getDescripcion());
        assertEquals("Transferencia", resultado.getMetodo().getNombre());
        assertEquals(200.0, resultado.getMonto());
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(pagoRepository).deleteById(1L);
        pagoService.eliminarPorId(1L);
        verify(pagoRepository).deleteById(1L);
    }
}