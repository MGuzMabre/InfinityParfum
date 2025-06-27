package com.infinityparfum.Pago.controller;

import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Tag(name = "Pagos", description = "Gestíon de pagos asociados a pedidos")
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Listar pagos")
    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(summary = "Crear pago")
    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        // Validar que el pedido exista y obtener el total
        String url = "http://localhost:8084/pedidos/" + pago.getPedidoId();
        try {
            // Suponiendo que el pedido tiene un campo 'total'
            Map pedido = restTemplate.getForObject(url, Map.class);
            if (pedido == null || !pedido.containsKey("total")) {
                throw new RuntimeException("No se pudo obtener el total del pedido.");
            }
            pago.setMonto(Double.valueOf(pedido.get("total").toString()));
        } catch (Exception e) {
            throw new RuntimeException("El pedido con ID " + pago.getPedidoId() + " no existe o no se pudo consultar.");
        }
        return ResponseEntity.ok(pagoService.crearPago(pago));
    }

    @Operation(summary = "Obtener pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar pago")
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.actualizarPago(id, pago));
    }

    @Operation(summary = "Eliminar pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}