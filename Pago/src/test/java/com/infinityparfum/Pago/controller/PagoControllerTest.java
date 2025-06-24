package com.infinityparfum.Pago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1L);
        pago.setPedidoId(10L);
        pago.setDescripcion("desc");
        pago.setMetodo(new MetodoPago(1, "Tarjeta", null));
        pago.setMonto(100.0);
    }

    @Test
    void testListarPagos() throws Exception {
        when(pagoService.obtenerTodos()).thenReturn(List.of(pago));
        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testCrearPago() throws Exception {
        when(pagoService.crearPago(any(Pago.class))).thenReturn(pago);
        mockMvc.perform(post("/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPagoPorId() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(pago);
        mockMvc.perform(get("/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarPago() throws Exception {
        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(pago);
        mockMvc.perform(put("/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testEliminarPago() throws Exception {
        doNothing().when(pagoService).eliminarPorId(1L);
        mockMvc.perform(delete("/pagos/1"))
                .andExpect(status().isNoContent());
    }
}