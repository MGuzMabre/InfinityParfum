package com.infinityparfum.Productos.controller;

import com.infinityparfum.Productos.model.Ingrediente;
import com.infinityparfum.Productos.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@Tag(name = "Ingredientes", description = "CRUD de ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @Operation(summary = "Listar ingredientes")
    @GetMapping
    public ResponseEntity<List<Ingrediente>> listar() {
        return ResponseEntity.ok(ingredienteService.obtenerTodos());
    }

    @Operation(summary = "Crear ingrediente")
    @PostMapping
    public ResponseEntity<Ingrediente> crear(@RequestBody Ingrediente ingrediente) {
        return ResponseEntity.ok(ingredienteService.agregarIngrediente(ingrediente));
    }

    @Operation(summary = "Obtener ingrediente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredienteService.buscarPorId(id));
    }

    @Operation(summary = "Actualizar ingrediente")
    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizar(@PathVariable Integer id, @RequestBody Ingrediente ingrediente) {
        return ResponseEntity.ok(ingredienteService.actualizarIngrediente(id, ingrediente));
    }

    @Operation(summary = "Eliminar ingrediente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ingredienteService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
