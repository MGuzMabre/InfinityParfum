package com.infinityparfum.Productos.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;


@Entity
@Schema(description = "Representa un producto en el catálogo de Infinity Parfum")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Schema(description = "Nombre del producto", example = "Perfume Afnam 9am")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Producto fresco especial para el verano")
    private String descripcion;

    @NotNull
    @Positive
    @Schema(description = "Precio del producto", example = "30000.0")
    private BigDecimal precio;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Stock disponible", example = "10")
    private Integer stock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}