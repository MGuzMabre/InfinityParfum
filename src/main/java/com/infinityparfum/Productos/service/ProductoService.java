package com.infinityparfum.Productos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.infinityparfum.Productos.model.Producto;

import org.springframework.http.HttpStatus;

@Service
public class ProductoService {

    private List<Producto> listaProductos = new ArrayList<>();

    public ProductoService() {
        listaProductos.add(new Producto(1L, "Perfume A", "Fragancia floral", 50.0));
        listaProductos.add(new Producto(2L, "Perfume B", "Fragancia cítrica", 60.0));
        listaProductos.add(new Producto(3L, "Perfume C", "Fragancia amaderada", 70.0));
    }

    public List<Producto> obtenerTodos() {
        return listaProductos;
    }

    public Producto agregarProducto(Producto producto) {
        listaProductos.add(producto);
        return producto;
    }

    public List<Producto> agregarProductos(List<Producto> productos) {
        listaProductos.addAll(productos);
        return productos;
    }

    public Producto buscarPorId(Long id) {
        return listaProductos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto actualizarProducto(Long id, Producto datosActualizados) {
        Producto productoExistente = buscarPorId(id);
        productoExistente.setNombre(datosActualizados.getNombre());
        productoExistente.setDescripcion(datosActualizados.getDescripcion());
        productoExistente.setPrecio(datosActualizados.getPrecio());
        return productoExistente;
    }

    public void eliminarPorId(Long id) {
        Producto productoAEliminar = buscarPorId(id);
        listaProductos.remove(productoAEliminar);
    }
}