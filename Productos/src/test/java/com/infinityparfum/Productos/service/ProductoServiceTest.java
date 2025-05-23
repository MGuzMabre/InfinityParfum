package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Producto;
import com.infinityparfum.Productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock private ProductoRepository productoRepository;

    @InjectMocks private ProductoService productoService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testBuscarPorId() {
        Producto producto = new Producto(); producto.setNombre("Perfume");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscarPorId(1L);
        assertEquals("Perfume", resultado.getNombre());
        verify(productoRepository).findById(1L);
    }
}
