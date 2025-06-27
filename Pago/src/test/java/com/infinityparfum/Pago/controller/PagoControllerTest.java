package com.infinityparfum.Pago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @MockBean
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Pago pago;
    private MetodoPago metodoPago;

    @BeforeEach
    void setUp() {
        metodoPago = new MetodoPago(1, "Tarjeta", "Visa, MasterCard");
        pago = new Pago();
        pago.setId(1L);
        pago.setPedidoId(10L);
        pago.setDescripcion("desc");
        pago.setMetodo(metodoPago);
        pago.setMonto(100.0);
    }

    @Test
    void testListarPagos() throws Exception {
        when(pagoService.obtenerTodos()).thenReturn(List.of(pago));
        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].metodo.nombre").value("Tarjeta"));
    }

    @Test
    void testCrearPago() throws Exception {
        // Simula que el pedido existe y tiene un campo 'total'
        when(restTemplate.getForObject(contains("/pedidos/10"), eq(Map.class)))
            .thenReturn(Map.of("total", 100.0));
        when(pagoService.crearPago(any(Pago.class))).thenReturn(pago);

        mockMvc.perform(post("/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.metodo.nombre").value("Tarjeta"));
    }

    @Test
    void testObtenerPagoPorId() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(pago);
        mockMvc.perform(get("/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.metodo.nombre").value("Tarjeta"));
    }

    @Test
    void testActualizarPago() throws Exception {
        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(pago);
        mockMvc.perform(put("/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.metodo.nombre").value("Tarjeta"));
    }

    @Test
    void testEliminarPago() throws Exception {
        doNothing().when(pagoService).eliminarPorId(1L);
        mockMvc.perform(delete("/pagos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCrearPagoConPedido() throws Exception {
        when(pagoService.crearPago(any(Pago.class))).thenReturn(pago);
        when(restTemplate.getForObject(contains("/pedidos/10"), eq(Map.class)))
                .thenReturn(Map.of("total", 100.0));

        mockMvc.perform(post("/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.metodo.nombre").value("Tarjeta"))
                .andExpect(jsonPath("$.monto").value(100.0));
    }
}