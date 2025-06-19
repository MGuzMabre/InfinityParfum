package com.infinityparfum.Envio.controller;

import com.infinityparfum.Envio.model.ZonaEnvio;
import com.infinityparfum.Envio.service.ZonaEnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zonas-envio")
@Tag(name = "Zonas de Envío", description = "Gestión de zonas de entrega y tarifas")
public class ZonaEnvioController {

    @Autowired
    private ZonaEnvioService service;

    @Operation(summary = "Listar zonas de envío")
    @GetMapping
    public ResponseEntity<List<ZonaEnvio>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Crear zona de envío")
    @PostMapping
    public ResponseEntity<ZonaEnvio> crear(@RequestBody ZonaEnvio zona) {
        return ResponseEntity.ok(service.agregar(zona));
    }

    @Operation(summary = "Obtener zona por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ZonaEnvio> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Actualizar zona de envío")
    @PutMapping("/{id}")
    public ResponseEntity<ZonaEnvio> actualizar(@PathVariable Integer id, @RequestBody ZonaEnvio zona) {
        return ResponseEntity.ok(service.actualizar(id, zona));
    }

    @Operation(summary = "Eliminar zona de envío")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
