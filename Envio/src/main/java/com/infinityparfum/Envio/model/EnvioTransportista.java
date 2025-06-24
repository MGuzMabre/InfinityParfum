package com.infinityparfum.Envio.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "envio_transportista")
public class EnvioTransportista {

    @EmbeddedId
    private EnvioTransportistaId id;

    public EnvioTransportistaId getId() { return id; }
    public void setId(EnvioTransportistaId id) { this.id = id; }
}

@Embeddable
class EnvioTransportistaId implements Serializable {

    private Long envioId;
    private Long transportistaId;

    public Long getEnvioId() { return envioId; }
    public void setEnvioId(Long envioId) { this.envioId = envioId; }

    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }
}
