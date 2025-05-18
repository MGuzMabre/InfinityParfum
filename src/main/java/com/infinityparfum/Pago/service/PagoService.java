package com.infinityparfum.Pago.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.infinityparfum.Pago.model.Pago;

import org.springframework.http.HttpStatus;

@Service
public class PagoService {

    private List<Pago> listaPagos = new ArrayList<>();

    public PagoService() {
        listaPagos.add(new Pago(1L, "Pago 1", "Tarjeta de Crédito", 150.0));
        listaPagos.add(new Pago(2L, "Pago 2", "PayPal", 200.0));
        listaPagos.add(new Pago(3L, "Pago 3", "Transferencia Bancaria", 300.0));
    }

    public List<Pago> obtenerTodos() {
        return listaPagos;
    }

    public Pago agregarPago(Pago pago) {
        listaPagos.add(pago);
        return pago;
    }

    public List<Pago> agregarPagos(List<Pago> pagos) {
        listaPagos.addAll(pagos);
        return pagos;
    }
    
    public Pago buscarPorId(Long id) {
        return listaPagos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
    }

    public Pago actualizarPago(Long id, Pago datosActualizados) {
        Pago pagoExistente = buscarPorId(id);
        pagoExistente.setDescripcion(datosActualizados.getDescripcion());
        pagoExistente.setMetodo(datosActualizados.getMetodo());
        pagoExistente.setMonto(datosActualizados.getMonto());
        return pagoExistente;
    }

    public void eliminarPorId(Long id) {
        Pago pagoAEliminar = buscarPorId(id);
        listaPagos.remove(pagoAEliminar);
    }
}