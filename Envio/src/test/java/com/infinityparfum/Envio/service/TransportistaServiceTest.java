package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Transportista;
import com.infinityparfum.Envio.repository.TransportistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportistaServiceTest {

    @Mock private TransportistaRepository transportistaRepository;

    @InjectMocks private TransportistaService transportistaService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Transportista t = new Transportista();
        when(transportistaRepository.findAll()).thenReturn(List.of(t));
        List<Transportista> resultado = transportistaService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(transportistaRepository).findAll();
    }

    @Test
    void testAgregar() {
        Transportista t = new Transportista();
        when(transportistaRepository.save(t)).thenReturn(t);
        Transportista resultado = transportistaService.agregar(t);
        assertEquals(t, resultado);
        verify(transportistaRepository).save(t);
    }

    @Test
    void testBuscarPorIdExiste() {
        Transportista t = new Transportista();
        when(transportistaRepository.findById(1L)).thenReturn(Optional.of(t));
        Transportista resultado = transportistaService.buscarPorId(1L);
        assertEquals(t, resultado);
        verify(transportistaRepository).findById(1L);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(transportistaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> transportistaService.buscarPorId(1L));
    }

    @Test
    void testActualizarExiste() {
        Transportista tExistente = new Transportista();
        tExistente.setNombre("Viejo");
        Transportista datos = new Transportista();
        datos.setNombre("Nuevo");

        when(transportistaRepository.findById(1L)).thenReturn(Optional.of(tExistente));
        when(transportistaRepository.save(any(Transportista.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transportista resultado = transportistaService.actualizar(1L, datos);

        assertEquals("Nuevo", resultado.getNombre());
    }

    @Test
    void testActualizarNoExiste() {
        Transportista datos = new Transportista();
        when(transportistaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> transportistaService.actualizar(1L, datos));
    }

    @Test
    void testEliminar() {
        doNothing().when(transportistaRepository).deleteById(1L);
        transportistaService.eliminar(1L);
        verify(transportistaRepository).deleteById(1L);
    }
}