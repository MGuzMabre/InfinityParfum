package com.infinityparfum.Pedidos.controller;
import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.service.PedidosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
 
@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    private static final Logger logger = LoggerFactory.getLogger(PedidosController.class);
    @Autowired
    private PedidosService pedidosService;

    @GetMapping
    public ResponseEntity<List<Pedidos>> listarPedidos() {
        logger.info("Listando todos los pedidos");
        return ResponseEntity.ok(pedidosService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Pedidos pedido) {
        try {
            logger.info("Creando un nuevo pedido para el cliente con ID: {}", pedido.getClienteId());
            Pedidos nuevoPedido = pedidosService.agregarPedido(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (Exception e) {
            logger.error("Error al crear el pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pedido: " + e.getMessage());
        }
    }

    @PutMapping("/{pedidoId}/pago/{pagoId}")
    public ResponseEntity<?> asociarPago(@PathVariable Long pedidoId, @PathVariable Long pagoId) {
        try {
            logger.info("Asociando el pago con ID: {} al pedido con ID: {}", pagoId, pedidoId);
            Pedidos pedidoActualizado = pedidosService.asociarPago(pedidoId, pagoId);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            logger.error("Error al asociar el pago: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asociar el pago: " + e.getMessage());
        }
    }

    @PutMapping("/{pedidoId}/envio/{envioId}")
    public ResponseEntity<?> asociarEnvio(@PathVariable Long pedidoId, @PathVariable Long envioId) {
        try {
            logger.info("Asociando el envío con ID: {} al pedido con ID: {}", envioId, pedidoId);
            Pedidos pedidoActualizado = pedidosService.asociarEnvio(pedidoId, envioId);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            logger.error("Error al asociar el envío: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asociar el envío: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            logger.info("Obteniendo el pedido con ID: {}", id);
            Pedidos pedido = pedidosService.obtenerPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            logger.error("Error al obtener el pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            logger.info("Eliminando el pedido con ID: {}", id);
            pedidosService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar el pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}