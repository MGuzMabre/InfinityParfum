package com.infinityparfum.Envio.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnvioTransportistaId implements Serializable {

    private Long envioId;
    private Long transportistaId;

    public EnvioTransportistaId() {}

    public EnvioTransportistaId(Long envioId, Long transportistaId) {
        this.envioId = envioId;
        this.transportistaId = transportistaId;
    }

    public Long getEnvioId() { return envioId; }
    public void setEnvioId(Long envioId) { this.envioId = envioId; }

    public Long getTransportistaId() { return transportistaId; }
    public void setTransportistaId(Long transportistaId) { this.transportistaId = transportistaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvioTransportistaId that = (EnvioTransportistaId) o;
        return Objects.equals(envioId, that.envioId) &&
               Objects.equals(transportistaId, that.transportistaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(envioId, transportistaId);
    }
}
