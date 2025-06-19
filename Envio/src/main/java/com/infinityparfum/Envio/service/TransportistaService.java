package com.infinityparfum.Envio.service;

import com.infinityparfum.Envio.model.Transportista;
import com.infinityparfum.Envio.repository.TransportistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TransportistaService {

    @Autowired
    private TransportistaRepository repository;

    public List<Transportista> obtenerTodos() {
        return repository.findAll();
    }

    public Transportista agregar(Transportista obj) {
        return repository.save(obj);
    }

    public Transportista buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transportista no encontrado"));
    }

    public Transportista actualizar(Long id, Transportista datos) {
        Transportista t = buscarPorId(id);
        t.setNombre(datos.getNombre());
        t.setRut(datos.getRut());
        t.setTelefono(datos.getTelefono());
        return repository.save(t);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}