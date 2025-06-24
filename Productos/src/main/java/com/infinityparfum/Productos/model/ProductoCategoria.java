package com.infinityparfum.Productos.model;
import jakarta.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "producto_categoria")
public class ProductoCategoria {
    @EmbeddedId
    private ProductoCategoriaId id;

    public ProductoCategoriaId getId() { return id; }
    public void setId(ProductoCategoriaId id) { this.id = id; }
}

@Embeddable
class ProductoCategoriaId implements Serializable {
    private Long productoId;
    private Integer categoriaId;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
}