package com.infinityparfum.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infinityparfum.Productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsById(Long id);
}