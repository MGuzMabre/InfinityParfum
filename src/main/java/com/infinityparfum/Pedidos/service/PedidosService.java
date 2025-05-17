package com.infinityparfum.Pedidos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.infinityparfum.Pedidos.model.Pedidos;

import org.springframework.http.HttpStatus;

@Service
public class PedidosService {

    private List<Pedidos> listaPedidos = new ArrayList<>();

    public PedidosService() {
        listaPedidos.add(new Pedidos(1L, "Pedido 1", "Cliente A", 100.0));
        listaPedidos.add(new Pedidos(2L, "Pedido 2", "Cliente B", 200.0));
        listaPedidos.add(new Pedidos(3L, "Pedido 3", "Cliente C", 300.0));
    }

    public List<Pedidos> obtenerTodos() {
        return listaPedidos;
    }

    public Pedidos agregarPedido(Pedidos pedido) {
        listaPedidos.add(pedido);
        return pedido;
    }

    public List<Pedidos> agregarPedidos(List<Pedidos> pedidos) {
        listaPedidos.addAll(pedidos);
        return pedidos;
    }

    public Pedidos buscarPorId(Long id) {
        return listaPedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    public Pedidos actualizarPedido(Long id, Pedidos datosActualizados) {
        Pedidos pedidoExistente = buscarPorId(id);
        pedidoExistente.setDescripcion(datosActualizados.getDescripcion());
        pedidoExistente.setCliente(datosActualizados.getCliente());
        pedidoExistente.setTotal(datosActualizados.getTotal());
        return pedidoExistente;
    }

    public void eliminarPorId(Long id) {
        Pedidos pedidoAEliminar = buscarPorId(id);
        listaPedidos.remove(pedidoAEliminar);
    }
}