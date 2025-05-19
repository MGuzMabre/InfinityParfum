package com.infinityparfum.Pedidos.service;

import com.infinityparfum.Pedidos.model.Pedidos;
import com.infinityparfum.Pedidos.model.ItemPedido;
import com.infinityparfum.Pedidos.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
        // Validar cliente
        String usuarioUrl = usuariosServiceUrl + "/usuarios/" + pedido.getClienteId() + "/existe";
        Boolean usuarioExiste = restTemplate.getForObject(usuarioUrl, Boolean.class);
        if (usuarioExiste == null || !usuarioExiste) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente con ID " + pedido.getClienteId() + " no existe.");
        }

        // Validar productos y reducir stock
        for (ItemPedido item : pedido.getItems()) {
            String productoUrl = productosServiceUrl + "/productos/" + item.getProductoId();
            restTemplate.getForObject(productoUrl, Object.class);

            String stockUrl = productosServiceUrl + "/productos/" + item.getProductoId() + "/reducir-stock?cantidad=" + item.getCantidad();
            restTemplate.put(stockUrl, null);
        }

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
}