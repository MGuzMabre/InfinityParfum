package com.infinityparfum.Pago.service;

import com.infinityparfum.Pago.model.MetodoPago;
import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.repository.PagoRepository;
import com.infinityparfum.Pago.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Value("${pedidos.service.url:http://localhost:8084}")
    private String pedidosServiceUrl;

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Pago crearPago(Pago pago) {
        // Validar que el pedido exista en el microservicio de Pedidos
        String url = "http://localhost:8084/pedidos/" + pago.getPedidoId();
        try {
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("El pedido con ID " + pago.getPedidoId() + " no existe.");
        }

        // Cargar el método de pago completo
        Integer metodoId = pago.getMetodo().getId();
        MetodoPago metodoCompleto = metodoPagoRepository.findById(metodoId)
            .orElseThrow(() -> new RuntimeException("Método de pago no encontrado con ID: " + metodoId));
        pago.setMetodo(metodoCompleto);

        Pago pagoGuardado = pagoRepository.save(pago);

        // Asociar el pago al pedido automáticamente
        String urlAsociarPago = pedidosServiceUrl + "/pedidos/" + pagoGuardado.getPedidoId() + "/pago/" + pagoGuardado.getId();
        try {
            restTemplate.put(urlAsociarPago, null);
        } catch (Exception e) {
            // Puedes loguear el error o lanzar una excepción si quieres que sea obligatorio
        }

        return pagoGuardado;
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