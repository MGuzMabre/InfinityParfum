package com.infinityparfum.Envio.repository;

import com.infinityparfum.Envio.model.EnvioTransportista;
import com.infinityparfum.Envio.model.EnvioTransportistaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioTransportistaRepository extends JpaRepository<EnvioTransportista, EnvioTransportistaId> {
}
