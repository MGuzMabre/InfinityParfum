package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.model.EnvioTransportista;
import com.infinityparfum.Envio.model.EnvioTransportistaId;
import com.infinityparfum.Envio.model.Transportista;
import com.infinityparfum.Envio.repository.EnvioRepository;
import com.infinityparfum.Envio.repository.EnvioTransportistaRepository;
import com.infinityparfum.Envio.repository.TransportistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pedidos.service.url:http://localhost:8084}")
    private String pedidosServiceUrl;

    @Autowired
    private EnvioTransportistaRepository envioTransportistaRepository;

    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    public Envio crearEnvio(Envio envio) {
        try {
            restTemplate.getForObject("http://localhost:8084/pedidos/" + envio.getPedidoId(), Object.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido con ID " + envio.getPedidoId() + " no existe.");
        }
        Envio envioGuardado = envioRepository.save(envio);

        // Asociar el envío al pedido automáticamente
        String urlAsociarEnvio = pedidosServiceUrl + "/pedidos/" + envioGuardado.getPedidoId() + "/envio/" + envioGuardado.getId();
        try {
            restTemplate.put(urlAsociarEnvio, null);
        } catch (Exception e) {
            // Puedes loguear el error o lanzar una excepción si quieres que sea obligatorio
        }

        return envioGuardado;
    }

    public Envio obtenerPorId(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Envio no encontrado con ID: " + id));
    }

    public Envio actualizarEnvio(Long id, Envio datos) {
        Envio envio = obtenerPorId(id);
        envio.setEstado(datos.getEstado());
        // ...otros campos que quieras actualizar...
        return envioRepository.save(envio);
    }

    public void eliminarPorId(Long id) {
        envioRepository.deleteById(id);
    }

    public Envio actualizarEstado(Long id, String nuevoEstado) {
        Envio envio = obtenerPorId(id);
        envio.setEstado(nuevoEstado);
        return envioRepository.save(envio);
    }

    public void asociarTransportista(Long envioId, Long transportistaId) {
        // Verifica que ambos existan
        Envio envio = envioRepository.findById(envioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Envío no encontrado"));
        Transportista transportista = transportistaRepository.findById(transportistaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transportista no encontrado"));

        // Crea la relación
        EnvioTransportistaId id = new EnvioTransportistaId();
        id.setEnvioId(envioId);
        id.setTransportistaId(transportistaId);

        EnvioTransportista relacion = new EnvioTransportista();
        relacion.setId(id);

        // Guarda la relación (debes tener un EnvioTransportistaRepository)
        envioTransportistaRepository.save(relacion);
    }
}