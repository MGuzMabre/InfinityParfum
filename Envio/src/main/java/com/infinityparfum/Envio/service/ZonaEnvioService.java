package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.ZonaEnvio;
import com.infinityparfum.Envio.repository.ZonaEnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ZonaEnvioService {

    @Autowired
    private ZonaEnvioRepository repository;

    public List<ZonaEnvio> obtenerTodos() {
        return repository.findAll();
    }

    public ZonaEnvio agregar(ZonaEnvio obj) {
        return repository.save(obj);
    }

    public ZonaEnvio buscarPorId(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona no encontrada"));
    }

    public ZonaEnvio actualizar(Integer id, ZonaEnvio datos) {
        ZonaEnvio z = buscarPorId(id);
        z.setNombre(datos.getNombre());
        z.setTarifa(datos.getTarifa());
        return repository.save(z);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
