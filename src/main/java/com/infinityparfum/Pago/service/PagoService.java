package com.infinityparfum.Pago.service;

import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    @Qualifier("pagoRestTemplate")
    private RestTemplate restTemplate;

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Pago crearPago(Pago pago) {
        // Validar que el pedido exista en el microservicio de Pedidos
        String url = "http://localhost:8082/pedidos/" + pago.getPedidoId();
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("El pedido con ID " + pago.getPedidoId() + " no existe.");
        }

        return pagoRepository.save(pago);
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    public Pago actualizarPago(Long id, Pago pago) {
        Pago pagoExistente = obtenerPorId(id);
        pagoExistente.setDescripcion(pago.getDescripcion());
        pagoExistente.setMetodo(pago.getMetodo());
        pagoExistente.setMonto(pago.getMonto());
        return pagoRepository.save(pagoExistente);
    }

    public void eliminarPorId(Long id) {
        pagoRepository.deleteById(id);
    }
}