package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.ZonaEnvio;
import com.infinityparfum.Envio.repository.ZonaEnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZonaEnvioServiceTest {

    @Mock private ZonaEnvioRepository zonaEnvioRepository;

    @InjectMocks private ZonaEnvioService zonaEnvioService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        ZonaEnvio zona = new ZonaEnvio();
        when(zonaEnvioRepository.findAll()).thenReturn(List.of(zona));
        List<ZonaEnvio> resultado = zonaEnvioService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(zonaEnvioRepository).findAll();
    }

    @Test
    void testAgregar() {
        ZonaEnvio zona = new ZonaEnvio();
        when(zonaEnvioRepository.save(zona)).thenReturn(zona);
        ZonaEnvio resultado = zonaEnvioService.agregar(zona);
        assertEquals(zona, resultado);
        verify(zonaEnvioRepository).save(zona);
    }

    @Test
    void testBuscarPorIdExiste() {
        ZonaEnvio zona = new ZonaEnvio();
        when(zonaEnvioRepository.findById(1)).thenReturn(Optional.of(zona));
        ZonaEnvio resultado = zonaEnvioService.buscarPorId(1);
        assertEquals(zona, resultado);
        verify(zonaEnvioRepository).findById(1);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(zonaEnvioRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> zonaEnvioService.buscarPorId(1));
    }

    @Test
    void testActualizarExiste() {
        ZonaEnvio zonaExistente = new ZonaEnvio();
        zonaExistente.setNombre("Vieja");
        ZonaEnvio datos = new ZonaEnvio();
        datos.setNombre("Nueva");

        when(zonaEnvioRepository.findById(1)).thenReturn(Optional.of(zonaExistente));
        when(zonaEnvioRepository.save(any(ZonaEnvio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ZonaEnvio resultado = zonaEnvioService.actualizar(1, datos);

        assertEquals("Nueva", resultado.getNombre());
    }

    @Test
    void testActualizarNoExiste() {
        ZonaEnvio datos = new ZonaEnvio();
        when(zonaEnvioRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> zonaEnvioService.actualizar(1, datos));
    }

    @Test
    void testEliminar() {
        doNothing().when(zonaEnvioRepository).deleteById(1);
        zonaEnvioService.eliminar(1);
        verify(zonaEnvioRepository).deleteById(1);
    }
}