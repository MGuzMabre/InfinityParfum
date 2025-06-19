package com.infinityparfum.Envio.repository;

import com.infinityparfum.Envio.model.ZonaEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaEnvioRepository extends JpaRepository<ZonaEnvio, Integer> {
}

