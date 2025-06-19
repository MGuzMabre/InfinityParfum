package com.infinityparfum.Pedidos.controller;

import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.service.PedidosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "Gestíon de pedidos de clientes")
@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Operation(summary = "Listar pedidos")
    @GetMapping
    public ResponseEntity<List<Pedidos>> listarPedidos() {
        return ResponseEntity.ok(pedidosService.obtenerTodos());
    }

    @Operation(summary = "Crear pedido")
    @PostMapping
    public ResponseEntity<Pedidos> crearPedido(@RequestBody Pedidos pedido) {
        return ResponseEntity.ok(pedidosService.agregarPedido(pedido));
    }

    @Operation(summary = "Asociar pago a pedido")
    @PutMapping("/{pedidoId}/pago/{pagoId}")
    public ResponseEntity<Pedidos> asociarPago(@PathVariable Long pedidoId, @PathVariable Long pagoId) {
        return ResponseEntity.ok(pedidosService.asociarPago(pedidoId, pagoId));
    }

    @Operation(summary = "Asociar envío a pedido")
    @PutMapping("/{pedidoId}/envio/{envioId}")
    public ResponseEntity<Pedidos> asociarEnvio(@PathVariable Long pedidoId, @PathVariable Long envioId) {
        return ResponseEntity.ok(pedidosService.asociarEnvio(pedidoId, envioId));
    }

    @Operation(summary = "Obtener pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pedidos> obtenerPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidosService.obtenerPorId(id));
    }

    @Operation(summary = "Eliminar pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidosService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
