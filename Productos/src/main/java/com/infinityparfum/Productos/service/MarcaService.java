package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Marca;
import com.infinityparfum.Productos.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> obtenerTodas() {
        return marcaRepository.findAll();
    }

    public Marca agregarMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Marca buscarPorId(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));
    }

    public Marca actualizarMarca(Integer id, Marca datos) {
        Marca marca = buscarPorId(id);
        marca.setNombre(datos.getNombre());
        return marcaRepository.save(marca);
    }

    public void eliminarPorId(Integer id) {
        marcaRepository.deleteById(id);
    }
}
