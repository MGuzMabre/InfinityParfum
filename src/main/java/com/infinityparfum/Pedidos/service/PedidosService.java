package com.infinityparfum.Pedidos.service;

import com.infinityparfum.Pedidos.model.ItemPedido;
import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    public List<Pedidos> obtenerTodos() {
        return pedidosRepository.findAll();
    }

    public Pedidos agregarPedido(Pedidos pedido) {
        for (ItemPedido item : pedido.getItems()) {
            item.setPedido(pedido);
        }
        return pedidosRepository.save(pedido);
    }

    public List<Pedidos> agregarPedidos(List<Pedidos> pedidos) {
        for (Pedidos pedido : pedidos) {
            for (ItemPedido item : pedido.getItems()) {
                item.setPedido(pedido);
            }
        }
        return pedidosRepository.saveAll(pedidos);
    }

    public Pedidos buscarPorId(Long id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    public Pedidos actualizarPedido(Long id, Pedidos datosActualizados) {
        Pedidos pedidoExistente = buscarPorId(id);

        pedidoExistente.setClienteId(datosActualizados.getClienteId());
        pedidoExistente.setTotal(datosActualizados.getTotal());
        pedidoExistente.setEstado(datosActualizados.getEstado());
        pedidoExistente.setPagoId(datosActualizados.getPagoId());
        pedidoExistente.setEnvioId(datosActualizados.getEnvioId());

        // Actualiza los ítems
        pedidoExistente.getItems().clear();
        for (ItemPedido item : datosActualizados.getItems()) {
            item.setPedido(pedidoExistente);
            pedidoExistente.getItems().add(item);
        }

        return pedidosRepository.save(pedidoExistente);
    }

    public void eliminarPorId(Long id) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
        }
        pedidosRepository.deleteById(id);
    }
}
