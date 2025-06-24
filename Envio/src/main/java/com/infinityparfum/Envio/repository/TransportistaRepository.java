package com.infinityparfum.Envio.repository;

import com.infinityparfum.Envio.model.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {
}
