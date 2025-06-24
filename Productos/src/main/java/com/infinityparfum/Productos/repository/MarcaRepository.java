package com.infinityparfum.Productos.repository;

import com.infinityparfum.Productos.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}