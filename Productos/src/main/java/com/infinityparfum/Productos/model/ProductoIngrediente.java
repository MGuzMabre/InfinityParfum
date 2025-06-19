package com.infinityparfum.Productos.model;
import jakarta.persistence.*;

import java.io.Serializable;



@Entity
@Table(name = "producto_ingrediente")
public class ProductoIngrediente {
    @EmbeddedId
    private ProductoIngredienteId id;

    public ProductoIngredienteId getId() { return id; }
    public void setId(ProductoIngredienteId id) { this.id = id; }
}

@Embeddable
class ProductoIngredienteId implements Serializable {
    private Long productoId;
    private Integer ingredienteId;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getIngredienteId() { return ingredienteId; }
    public void setIngredienteId(Integer ingredienteId) { this.ingredienteId = ingredienteId; }
}
