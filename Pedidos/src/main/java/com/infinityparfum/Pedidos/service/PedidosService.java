package com.infinityparfum.Pedidos.service;

import com.infinityparfum.Pedidos.model.ItemPedido;
import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.repository.PedidosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    @Value("${productos.service.url}")
    private String productosServiceUrl;

    @Value("${pagos.service.url}")
    private String pagosServiceUrl;

    @Value("${envios.service.url}")
    private String enviosServiceUrl;

    public Pedidos agregarPedido(Pedidos pedido) {
        // Validar existencia del cliente
        String usuarioUrl = usuariosServiceUrl + "/usuarios/" + pedido.getClienteId() + "/existe";
        Boolean usuarioExiste = restTemplate.getForObject(usuarioUrl, Boolean.class);
        if (usuarioExiste == null || !usuarioExiste) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El cliente con ID " + pedido.getClienteId() + " no existe.");
        }

        // Validar productos y reducir stock
        for (ItemPedido item : pedido.getItems()) {
            String productoUrl = productosServiceUrl + "/productos/" + item.getProductoId();
            try {
                restTemplate.getForObject(productoUrl, Object.class);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El producto con ID " + item.getProductoId() + " no existe o no está disponible.");
            }

            String stockUrl = productosServiceUrl + "/productos/" + item.getProductoId()
                    + "/reducir-stock?cantidad=" + item.getCantidad();
            restTemplate.put(stockUrl, null);

            // Asociar el pedido a cada item para mantener la relación bidireccional
            item.setPedido(pedido);
        }

        // Calcular total automáticamente
        double total = pedido.getItems().stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();
        pedido.setTotal(total);

        return pedidosRepository.save(pedido);
    }

    public Pedidos asociarPago(Long pedidoId, Long pagoId) {
        Pedidos pedido = pedidosRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        String pagoUrl = pagosServiceUrl + "/pagos/" + pagoId;
        restTemplate.getForObject(pagoUrl, Object.class);

        pedido.setPagoId(pagoId);
        return pedidosRepository.save(pedido);
    }

    public Pedidos asociarEnvio(Long pedidoId, Long envioId) {
        Pedidos pedido = pedidosRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        String envioUrl = enviosServiceUrl + "/envios/" + envioId;
        restTemplate.getForObject(envioUrl, Object.class);

        pedido.setEnvioId(envioId);
        return pedidosRepository.save(pedido);
    }

    public List<Pedidos> obtenerTodos() {
        return pedidosRepository.findAll();
    }

    public Pedidos obtenerPorId(Long id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con ID: " + id));
    }

    public void eliminarPorId(Long id) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado con ID: " + id);
        }
        pedidosRepository.deleteById(id);
    }
}
