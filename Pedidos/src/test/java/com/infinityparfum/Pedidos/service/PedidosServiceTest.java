package com.infinityparfum.Pedidos.service;

import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.repository.PedidosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidosServiceTest {

    @Mock private PedidosRepository pedidosRepository;

    @InjectMocks private PedidosService pedidosService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testObtenerPorId() {
        Pedidos pedido = new Pedidos(); pedido.setClienteId(123L);
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedidos resultado = pedidosService.obtenerPorId(1L);
        assertEquals(123L, resultado.getClienteId());
        verify(pedidosRepository).findById(1L);
    }
}
