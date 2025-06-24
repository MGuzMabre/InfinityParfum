package com.infinityparfum.Pedidos.service;

import com.infinityparfum.Pedidos.model.ItemPedido;
import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.repository.PedidosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidosServiceTest {

    @Mock private PedidosRepository pedidosRepository;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private PedidosService pedidosService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testAgregarPedidoExitoso() {
        Pedidos pedido = new Pedidos();
        pedido.setClienteId(1L);
        pedido.setItems(new ArrayList<>());
        pedido.setEstado("Pendiente");

        ItemPedido item = new ItemPedido();
        item.setProductoId(10L);
        item.setCantidad(2);
        item.setPrecioUnitario(100.0);
        pedido.getItems().add(item);

        // Mock cliente existe
        when(restTemplate.getForObject(contains("/usuarios/1/existe"), eq(Boolean.class))).thenReturn(true);
        // Mock producto existe
        when(restTemplate.getForObject(contains("/productos/10"), eq(Object.class))).thenReturn(new Object());
        // Mock reducir stock
        doNothing().when(restTemplate).put(contains("/productos/10/reducir-stock?cantidad=2"), isNull());
        // Mock save
        when(pedidosRepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedidos resultado = pedidosService.agregarPedido(pedido);

        assertEquals(200.0, resultado.getTotal());
        assertEquals("Pendiente", resultado.getEstado());
        assertEquals(1, resultado.getItems().size());
        verify(pedidosRepository).save(pedido);
    }

    @Test
    void testAgregarPedidoClienteNoExiste() {
        Pedidos pedido = new Pedidos();
        pedido.setClienteId(2L);
        pedido.setItems(new ArrayList<>());

        when(restTemplate.getForObject(contains("/usuarios/2/existe"), eq(Boolean.class))).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.agregarPedido(pedido));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("no existe"));
    }

    @Test
    void testAgregarPedidoProductoNoExiste() {
        Pedidos pedido = new Pedidos();
        pedido.setClienteId(1L);
        pedido.setItems(new ArrayList<>());
        ItemPedido item = new ItemPedido();
        item.setProductoId(99L);
        item.setCantidad(1);
        item.setPrecioUnitario(50.0);
        pedido.getItems().add(item);

        when(restTemplate.getForObject(contains("/usuarios/1/existe"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForObject(contains("/productos/99"), eq(Object.class))).thenThrow(new RuntimeException("No existe"));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.agregarPedido(pedido));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("producto con ID 99"));
    }

    @Test
    void testAsociarPagoExitoso() {
        Pedidos pedido = new Pedidos();
        pedido.setId(1L);
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(restTemplate.getForObject(contains("/pagos/5"), eq(Object.class))).thenReturn(new Object());
        when(pedidosRepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedidos resultado = pedidosService.asociarPago(1L, 5L);

        assertEquals(5L, resultado.getPagoId());
        verify(pedidosRepository).save(pedido);
    }

    @Test
    void testAsociarPagoPedidoNoExiste() {
        when(pedidosRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.asociarPago(1L, 5L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAsociarEnvioExitoso() {
        Pedidos pedido = new Pedidos();
        pedido.setId(1L);
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(restTemplate.getForObject(contains("/envios/7"), eq(Object.class))).thenReturn(new Object());
        when(pedidosRepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedidos resultado = pedidosService.asociarEnvio(1L, 7L);

        assertEquals(7L, resultado.getEnvioId());
        verify(pedidosRepository).save(pedido);
    }

    @Test
    void testAsociarEnvioPedidoNoExiste() {
        when(pedidosRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.asociarEnvio(1L, 7L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testObtenerTodos() {
        Pedidos pedido = new Pedidos();
        when(pedidosRepository.findAll()).thenReturn(List.of(pedido));
        List<Pedidos> resultado = pedidosService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(pedidosRepository).findAll();
    }

    @Test
    void testObtenerPorIdExiste() {
        Pedidos pedido = new Pedidos();
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Pedidos resultado = pedidosService.obtenerPorId(1L);
        assertEquals(pedido, resultado);
        verify(pedidosRepository).findById(1L);
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(pedidosRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.obtenerPorId(1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testEliminarPorIdExiste() {
        when(pedidosRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidosRepository).deleteById(1L);
        pedidosService.eliminarPorId(1L);
        verify(pedidosRepository).deleteById(1L);
    }

    @Test
    void testEliminarPorIdNoExiste() {
        when(pedidosRepository.existsById(1L)).thenReturn(false);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pedidosService.eliminarPorId(1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}