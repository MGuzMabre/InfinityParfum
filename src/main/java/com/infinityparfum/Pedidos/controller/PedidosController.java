package com.infinityparfum.Pedidos.controller;

import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @GetMapping
    public List<Pedidos> listarPedidos() {
        return pedidosService.obtenerTodos();
    }

    @PostMapping
    public Pedidos crearPedido(@RequestBody Pedidos pedido) {
        return pedidosService.agregarPedido(pedido);
    }

    @PostMapping("/lista")
    public List<Pedidos> crearVariosPedidos(@RequestBody List<Pedidos> pedidos) {
        return pedidosService.agregarPedidos(pedidos);
    }

    @GetMapping("/{id}")
    public Pedidos obtenerPorId(@PathVariable Long id) {
        return pedidosService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Pedidos actualizar(@PathVariable Long id, @RequestBody Pedidos pedido) {
        return pedidosService.actualizarPedido(id, pedido);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pedidosService.eliminarPorId(id);
    }
}
