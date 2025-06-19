// MarcaController.java
package com.infinityparfum.Productos.controller;

import com.infinityparfum.Productos.model.Marca;
import com.infinityparfum.Productos.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
@Tag(name = "Marcas", description = "CRUD de marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Listar marcas")
    @GetMapping
    public ResponseEntity<List<Marca>> listar() {
        return ResponseEntity.ok(marcaService.obtenerTodas());
    }

    @Operation(summary = "Crear marca")
    @PostMapping
    public ResponseEntity<Marca> crear(@RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.agregarMarca(marca));
    }

    @Operation(summary = "Obtener marca por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @Operation(summary = "Actualizar marca")
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizar(@PathVariable Integer id, @RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.actualizarMarca(id, marca));
    }

    @Operation(summary = "Eliminar marca")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        marcaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}