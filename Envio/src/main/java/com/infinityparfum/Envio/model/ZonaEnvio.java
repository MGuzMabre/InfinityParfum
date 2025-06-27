package com.infinityparfum.Envio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Zona geográfica de envío con tarifa")
public class ZonaEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Integer id;

    @NotNull
    @Schema(example = "Santiago Centro")
    private String nombre;

    @NotNull
    @Schema(example = "3500")
    private Double tarifa;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getTarifa() { return tarifa; }
    public void setTarifa(Double tarifa) { this.tarifa = tarifa; }
}
