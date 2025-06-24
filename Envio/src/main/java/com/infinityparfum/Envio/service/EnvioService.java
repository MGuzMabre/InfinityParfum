package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    public Envio crearEnvio(Envio envio) {
        try {
            restTemplate.getForObject("http://localhost:8084/pedidos/" + envio.getPedidoId(), Object.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido con ID " + envio.getPedidoId() + " no existe.");
        }
        return envioRepository.save(envio);
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
}