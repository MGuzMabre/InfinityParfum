package com.InfinityParfum.Productos.Repository;

import com.InfinityParfum.Productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}







