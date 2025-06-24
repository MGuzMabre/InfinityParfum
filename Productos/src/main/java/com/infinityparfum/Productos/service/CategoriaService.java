package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Categoria;
import com.infinityparfum.Productos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria agregarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    public Categoria actualizarCategoria(Integer id, Categoria datos) {
        Categoria categoria = buscarPorId(id);
        categoria.setNombre(datos.getNombre());
        return categoriaRepository.save(categoria);
    }

    public void eliminarPorId(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
