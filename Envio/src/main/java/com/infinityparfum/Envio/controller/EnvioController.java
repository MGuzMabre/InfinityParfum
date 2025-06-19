package com.infinityparfum.Envio.controller;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;
    // Endpoint para listar todos los envíos
    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        return ResponseEntity.ok(envioService.obtenerTodos());
    }
    
    // Endpoint para crear un nuevo envío
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.crearEnvio(envio));
    }
    // Endpoint para obtener un envío por ID
    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }
    // Endpoint para actualizar un envío
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.actualizarEnvio(id, envio));
    }
    // Endpoint para actualizar el estado de un envío
    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }
    // Endpoint para eliminar un envío
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}