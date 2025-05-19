package com.infinityparfum.Productos.service;

import com.infinityparfum.Productos.model.Producto;
import com.infinityparfum.Productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.http.HttpStatus;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto actualizarProducto(Long id, Producto datosActualizados) {
        Producto productoExistente = buscarPorId(id);
        productoExistente.setNombre(datosActualizados.getNombre());
        productoExistente.setDescripcion(datosActualizados.getDescripcion());
        productoExistente.setPrecio(datosActualizados.getPrecio());
        productoExistente.setStock(datosActualizados.getStock());
        return productoRepository.save(productoExistente);
    }

    public void eliminarPorId(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public void reducirStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        if (producto.getStock() < cantidad) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    public void aumentarStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);
    }
}