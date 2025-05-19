package com.infinityparfum.Productos.controller;

import com.infinityparfum.Productos.model.Producto;
import com.infinityparfum.Productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.agregarProducto(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reducir-stock")
    public ResponseEntity<Void> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        productoService.reducirStock(id, cantidad);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/aumentar-stock")
    public ResponseEntity<Void> aumentarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        productoService.aumentarStock(id, cantidad);
        return ResponseEntity.noContent().build();
    }
}