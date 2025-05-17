package com.infinityparfum.Pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.service.PedidosService;

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
    public Pedidos crearPedido(@RequestBody Pedidos pedidos) {
        return pedidosService.agregarPedido(pedidos);
    }

    @PostMapping("/lista")
    public List<Pedidos> crearPedidos(@RequestBody List<Pedidos> pedidos) {
        return pedidosService.agregarPedidos(pedidos);
    }

    @GetMapping("/{id}")
    public Pedidos obtenerPedidoPorId(@PathVariable Long id) {
        return pedidosService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Pedidos actualizarPedido(@PathVariable Long id, @RequestBody Pedidos pedidos) {
        return pedidosService.actualizarPedido(id, pedidos);
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable Long id) {
        pedidosService.eliminarPorId(id);
    }
}