package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Marca;
import com.infinityparfum.Productos.repository.MarcaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarcaServiceTest {

    @Mock private MarcaRepository marcaRepository;

    @InjectMocks private MarcaService marcaService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodas() {
        Marca marca = new Marca();
        when(marcaRepository.findAll()).thenReturn(List.of(marca));
        List<Marca> resultado = marcaService.obtenerTodas();
        assertEquals(1, resultado.size());
        verify(marcaRepository).findAll();
    }

    @Test
    void testAgregarMarca() {
        Marca marca = new Marca();
        when(marcaRepository.save(marca)).thenReturn(marca);
        Marca resultado = marcaService.agregarMarca(marca);
        assertEquals(marca, resultado);
        verify(marcaRepository).save(marca);
    }

    @Test
    void testBuscarPorIdExiste() {
        Marca marca = new Marca();
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marca));
        Marca resultado = marcaService.buscarPorId(1);
        assertEquals(marca, resultado);
        verify(marcaRepository).findById(1);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(marcaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> marcaService.buscarPorId(1));
    }

    @Test
    void testActualizarMarca() {
        Marca marcaExistente = new Marca();
        marcaExistente.setNombre("Vieja");
        Marca datos = new Marca();
        datos.setNombre("Nueva");
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marcaExistente));
        when(marcaRepository.save(any(Marca.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Marca resultado = marcaService.actualizarMarca(1, datos);
        assertEquals("Nueva", resultado.getNombre());
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(marcaRepository).deleteById(1);
        marcaService.eliminarPorId(1);
        verify(marcaRepository).deleteById(1);
    }
}