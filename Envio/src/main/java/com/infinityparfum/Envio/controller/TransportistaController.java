package com.infinityparfum.Envio.controller;

import com.infinityparfum.Envio.model.Transportista;
import com.infinityparfum.Envio.service.TransportistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transportistas")
@Tag(name = "Transportistas", description = "Gestión de transportistas")
public class TransportistaController {

    @Autowired
    private TransportistaService service;

    @Operation(summary = "Listar transportistas")
    @GetMapping
    public ResponseEntity<List<Transportista>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Crear transportista")
    @PostMapping
    public ResponseEntity<Transportista> crear(@RequestBody Transportista t) {
        return ResponseEntity.ok(service.agregar(t));
    }

    @Operation(summary = "Obtener transportista por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transportista> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Actualizar transportista")
    @PutMapping("/{id}")
    public ResponseEntity<Transportista> actualizar(@PathVariable Long id, @RequestBody Transportista t) {
        return ResponseEntity.ok(service.actualizar(id, t));
    }

    @Operation(summary = "Eliminar transportista")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

