package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Producto;
import com.infinityparfum.Productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock private ProductoRepository productoRepository;

    @InjectMocks private ProductoService productoService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerTodos() {
        Producto producto = new Producto();
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<Producto> resultado = productoService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void testAgregarProducto() {
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto resultado = productoService.agregarProducto(producto);
        assertEquals(producto, resultado);
        verify(productoRepository).save(producto);
    }

    @Test
    void testBuscarPorIdExiste() {
        Producto producto = new Producto(); producto.setNombre("Perfume");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        Producto resultado = productoService.buscarPorId(1L);
        assertEquals("Perfume", resultado.getNombre());
        verify(productoRepository).findById(1L);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> productoService.buscarPorId(1L));
    }

    @Test
    void testActualizarProducto() {
        Producto productoExistente = new Producto();
        productoExistente.setNombre("Viejo");
        productoExistente.setDescripcion("desc");
        productoExistente.setPrecio(new BigDecimal("10.0"));
        productoExistente.setStock(5);

        Producto datos = new Producto();
        datos.setNombre("Nuevo");
        datos.setDescripcion("nueva desc");
        datos.setPrecio(new BigDecimal("20.0"));
        datos.setStock(10);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = productoService.actualizarProducto(1L, datos);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("nueva desc", resultado.getDescripcion());
        assertTrue(new BigDecimal("20.0").compareTo(resultado.getPrecio()) == 0);
        assertEquals(10, resultado.getStock());
    }

    @Test
    void testEliminarPorIdExiste() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1L);
        productoService.eliminarPorId(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void testEliminarPorIdNoExiste() {
        when(productoRepository.existsById(1L)).thenReturn(false);
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> productoService.eliminarPorId(1L));
    }

    @Test
    void testReducirStockExitoso() {
        Producto producto = new Producto();
        producto.setStock(10);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        productoService.reducirStock(1L, 5);
        assertEquals(5, producto.getStock());
    }

    @Test
    void testReducirStockInsuficiente() {
        Producto producto = new Producto();
        producto.setStock(3);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> productoService.reducirStock(1L, 5));
    }

    @Test
    void testAumentarStock() {
        Producto producto = new Producto();
        producto.setStock(5);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        productoService.aumentarStock(1L, 7);
        assertEquals(12, producto.getStock());
    }
}