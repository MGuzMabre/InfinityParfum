package com.infinityparfum.Pago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.repository.MetodoPagoRepository;
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

@WebMvcTest(MetodoPagoController.class)
class MetodoPagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetodoPagoRepository metodoPagoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MetodoPago metodoPago;

    @BeforeEach
    void setUp() {
        metodoPago = new MetodoPago(1, "Tarjeta", "Visa");
    }

    @Test
    void testListarMetodosPago() throws Exception {
        when(metodoPagoRepository.findAll()).thenReturn(List.of(metodoPago));
        mockMvc.perform(get("/metodos-pago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tarjeta"));
    }

    @Test
    void testCrearMetodoPago() throws Exception {
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenReturn(metodoPago);
        mockMvc.perform(post("/metodos-pago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metodoPago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tarjeta"));
    }
}