package com.infinityparfum.Pedidos.repository;

import com.infinityparfum.Pedidos.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
}
// Este repositorio extiende JpaRepository, lo que proporciona métodos CRUD básicos para la entidad Pedidos.