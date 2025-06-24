package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Ingrediente;
import com.infinityparfum.Productos.repository.IngredienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredienteServiceTest {

    @Mock private IngredienteRepository ingredienteRepository;

    @InjectMocks private IngredienteService ingredienteService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Ingrediente ingrediente = new Ingrediente();
        when(ingredienteRepository.findAll()).thenReturn(List.of(ingrediente));
        List<Ingrediente> resultado = ingredienteService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(ingredienteRepository).findAll();
    }

    @Test
    void testAgregarIngrediente() {
        Ingrediente ingrediente = new Ingrediente();
        when(ingredienteRepository.save(ingrediente)).thenReturn(ingrediente);
        Ingrediente resultado = ingredienteService.agregarIngrediente(ingrediente);
        assertEquals(ingrediente, resultado);
        verify(ingredienteRepository).save(ingrediente);
    }

    @Test
    void testBuscarPorIdExiste() {
        Ingrediente ingrediente = new Ingrediente();
        when(ingredienteRepository.findById(1)).thenReturn(Optional.of(ingrediente));
        Ingrediente resultado = ingredienteService.buscarPorId(1);
        assertEquals(ingrediente, resultado);
        verify(ingredienteRepository).findById(1);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(ingredienteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> ingredienteService.buscarPorId(1));
    }

    @Test
    void testActualizarIngrediente() {
        Ingrediente ingredienteExistente = new Ingrediente();
        ingredienteExistente.setNombre("Viejo");
        Ingrediente datos = new Ingrediente();
        datos.setNombre("Nuevo");
        when(ingredienteRepository.findById(1)).thenReturn(Optional.of(ingredienteExistente));
        when(ingredienteRepository.save(any(Ingrediente.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Ingrediente resultado = ingredienteService.actualizarIngrediente(1, datos);
        assertEquals("Nuevo", resultado.getNombre());
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(ingredienteRepository).deleteById(1);
        ingredienteService.eliminarPorId(1);
        verify(ingredienteRepository).deleteById(1);
    }
}