package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnvioServiceTest {

    @Mock private EnvioRepository envioRepository;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private EnvioService envioService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Envio envio = new Envio();
        when(envioRepository.findAll()).thenReturn(List.of(envio));
        List<Envio> resultado = envioService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(envioRepository).findAll();
    }

    @Test
    void testCrearEnvioExitoso() {
        Envio envio = new Envio();
        envio.setPedidoId(10L);
        envio.setEstado("Pendiente");

        when(restTemplate.getForObject(contains("/pedidos/10"), eq(Object.class))).thenReturn(new Object());
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.crearEnvio(envio);

        assertEquals(envio, resultado);
        verify(envioRepository).save(envio);
    }

    @Test
    void testCrearEnvioPedidoNoExiste() {
        Envio envio = new Envio();
        envio.setPedidoId(99L);

        when(restTemplate.getForObject(contains("/pedidos/99"), eq(Object.class))).thenThrow(new RuntimeException("No existe"));

        assertThrows(ResponseStatusException.class, () -> envioService.crearEnvio(envio));
        verify(envioRepository, never()).save(any());
    }

    @Test
    void testObtenerPorIdExiste() {
        Envio envio = new Envio();
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        Envio resultado = envioService.obtenerPorId(1L);
        assertEquals(envio, resultado);
        verify(envioRepository).findById(1L);
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> envioService.obtenerPorId(1L));
    }

    @Test
    void testActualizarEnvioExiste() {
        Envio envioExistente = new Envio();
        envioExistente.setEstado("Pendiente");
        Envio datos = new Envio();
        datos.setEstado("Enviado");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioExistente));
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Envio resultado = envioService.actualizarEnvio(1L, datos);

        assertEquals("Enviado", resultado.getEstado());
    }

    @Test
    void testActualizarEnvioNoExiste() {
        Envio datos = new Envio();
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> envioService.actualizarEnvio(1L, datos));
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(envioRepository).deleteById(1L);
        envioService.eliminarPorId(1L);
        verify(envioRepository).deleteById(1L);
    }

    @Test
    void testActualizarEstadoExitoso() {
        Envio envio = new Envio();
        envio.setEstado("Pendiente");
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Envio resultado = envioService.actualizarEstado(1L, "Entregado");

        assertEquals("Entregado", resultado.getEstado());
    }

    @Test
    void testActualizarEstadoNoExiste() {
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> envioService.actualizarEstado(1L, "Entregado"));
    }
}