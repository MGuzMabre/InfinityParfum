package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Envio;
import com.infinityparfum.Envio.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        // Validar que el pedido exista en el microservicio de Pedidos
        String url = "http://localhost:8084/pedidos/" + envio.getPedidoId();
        try { 
            restTemplate.getForObject(url, Object.class);
        } catch (Exception e) { // En caso de que el pedido no exista o haya un error
            throw new RuntimeException("El pedido con ID " + envio.getPedidoId() + " no existe.");
            
        }
        // Si el pedido existe, se procede a crear el envío con estado "Pendiente"
        envio.setEstado("Pendiente");
        return envioRepository.save(envio);
    }
    // Método para obtener un envío por ID
    public Envio obtenerPorId(Long id) {
        return envioRepository.findById(id) 
                .orElseThrow(() -> new RuntimeException("Envio no encontrado con ID: " + id)); // Manejo de excepción si no se encuentra el envío
    }

    public Envio actualizarEnvio(Long id, Envio envio) {
        Envio envioExistente = obtenerPorId(id);
        envioExistente.setDireccion(envio.getDireccion());
        envioExistente.setEstado(envio.getEstado());
        return envioRepository.save(envioExistente);
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