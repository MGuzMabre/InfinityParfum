package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Ingrediente;
import com.infinityparfum.Productos.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> obtenerTodos() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente agregarIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente buscarPorId(Integer id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no encontrado"));
    }

    public Ingrediente actualizarIngrediente(Integer id, Ingrediente datos) {
        Ingrediente ingrediente = buscarPorId(id);
        ingrediente.setNombre(datos.getNombre());
        return ingredienteRepository.save(ingrediente);
    }

    public void eliminarPorId(Integer id) {
        ingredienteRepository.deleteById(id);
    }
}
