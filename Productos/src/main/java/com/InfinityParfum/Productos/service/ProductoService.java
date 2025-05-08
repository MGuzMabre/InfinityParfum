package com.InfinityParfum.Productos.service;

import com.InfinityParfum.Productos.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public Producto guardar(Producto producto) {
        productos.add(producto);
        return producto;
    }

    public Producto obtenerPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void eliminar(Long id) {
        productos.removeIf(p -> p.getId().equals(id));
    }
}
