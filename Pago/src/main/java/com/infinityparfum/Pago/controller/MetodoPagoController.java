package com.infinityparfum.Pago.controller;

import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.repository.MetodoPagoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Métodos de Pago", description = "Gestión de métodos de pago")
@RestController
@RequestMapping("/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @GetMapping
    public List<MetodoPago> listar() {
        return metodoPagoRepository.findAll();
    }

    @PostMapping
    public MetodoPago crear(@RequestBody MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }
}
