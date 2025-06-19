package com.infinityparfum.Productos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidad que representa un producto disponible para la venta")
@Entity
public class Producto {

    @Schema(description = "ID único del producto", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del producto", example = "Perfume Ocean Blue")
    @NotNull
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Aroma fresco y duradero")
    @NotNull
    private String descripcion;

    @Schema(description = "Precio del producto", example = "19990")
    @NotNull
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @Schema(description = "Cantidad de stock disponible", example = "20")
    @NotNull
    @Positive(message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}