package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Categoria;
import com.infinityparfum.Productos.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;

    @InjectMocks private CategoriaService categoriaService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodas() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        List<Categoria> resultado = categoriaService.obtenerTodas();
        assertEquals(1, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void testAgregarCategoria() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria resultado = categoriaService.agregarCategoria(categoria);
        assertEquals(categoria, resultado);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testBuscarPorIdExiste() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        Categoria resultado = categoriaService.buscarPorId(1);
        assertEquals(categoria, resultado);
        verify(categoriaRepository).findById(1);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> categoriaService.buscarPorId(1));
    }

    @Test
    void testActualizarCategoria() {
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setNombre("Vieja");
        Categoria datos = new Categoria();
        datos.setNombre("Nueva");
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Categoria resultado = categoriaService.actualizarCategoria(1, datos);
        assertEquals("Nueva", resultado.getNombre());
    }

    @Test
    void testEliminarPorId() {
        doNothing().when(categoriaRepository).deleteById(1);
        categoriaService.eliminarPorId(1);
        verify(categoriaRepository).deleteById(1);
    }
}