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
