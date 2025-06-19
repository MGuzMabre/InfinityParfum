package com.infinityparfum.Pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    private String detalles;

    public MetodoPago() {
    }

    public MetodoPago(Integer id, String nombre, String detalles) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = detalles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
