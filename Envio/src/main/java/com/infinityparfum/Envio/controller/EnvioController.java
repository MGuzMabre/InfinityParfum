package com.infinityparfum.Envio.controller;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Envíos", description = "Gestíon de envíos de pedidos")
@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Operation(summary = "Listar envíos")
    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        return ResponseEntity.ok(envioService.obtenerTodos());
    }

    @Operation(summary = "Crear envío")
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.crearEnvio(envio));
    }

    @Operation(summary = "Obtener envío por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar envío")
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.actualizarEnvio(id, envio));
    }

    @Operation(summary = "Actualizar estado del envío")
    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }

    @Operation(summary = "Eliminar envío")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Asociar transportista a un envío")
    @PutMapping("/{envioId}/transportista/{transportistaId}")
    public ResponseEntity<Void> asociarTransportista(
            @PathVariable Long envioId,
            @PathVariable Long transportistaId) {
        envioService.asociarTransportista(envioId, transportistaId);
        return ResponseEntity.noContent().build();
    }
}