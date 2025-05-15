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

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        return ResponseEntity.ok(envioService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.crearEnvio(envio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.actualizarEnvio(id, envio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}