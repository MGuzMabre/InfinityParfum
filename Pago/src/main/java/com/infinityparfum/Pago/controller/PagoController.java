package com.infinityparfum.Pago.controller;

import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.crearPago(pago));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.actualizarPago(id, pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}