package com.infinityparfum.Pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Entidad que representa un pago realizado para un pedido",
    example = "{\n" +
            "  \"pedidoId\": 1,\n" +
            "  \"descripcion\": \"Pago con tarjeta\",\n" +
            "  \"metodo\": { \"id\": 1 }\n" +
            "}")
@Entity
@Table(name = "pago")
public class Pago {

    @Schema(description = "ID del pago", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del pedido asociado al pago", example = "12")
    @NotNull
    @Column(name = "pedido_id")
    private Long pedidoId;

    @Schema(description = "Descripción del pago", example = "Pago con tarjeta")
    @NotNull
    private String descripcion;

    @Schema(description = "Método de pago")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "metodo_id", nullable = false)
    private MetodoPago metodo;

    @Schema(description = "Monto del pago", example = "19990")
    @NotNull
    @Positive(message = "El monto debe ser mayor que 0")
    private Double monto;

    public Pago() {}

    public Pago(Long id, Long pedidoId, String descripcion, MetodoPago metodo, Double monto) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.descripcion = descripcion;
        this.metodo = metodo;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
