package com.infinityparfum.Envio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidad que representa el estado de envío de un pedido")
@Entity
@Table(name = "envios")
public class Envio {

    @Schema(description = "ID único del envío", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del pedido asociado", example = "10")
    @NotNull
    private Long pedidoId;

    @Schema(description = "Dirección de entrega", example = "Calle Falsa 123")
    @NotNull
    private String direccion;

    @Schema(description = "Estado actual del envío", example = "Pendiente")
    @NotNull
    private String estado;

    @Schema(description = "Fecha y hora del envío")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEnvio = LocalDateTime.now();


     
    public Long getId() {
        return id;
    }
    // Método para establecer el ID del envío
    public void setId(Long id) {
        this.id = id;
    }
    // Método para obtener el ID del pedido asociado al envío
    public Long getPedidoId() {
        return pedidoId;
    }
    // Método para establecer el ID del pedido asociado al envío
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    // Método para obtener la dirección de envío
    public String getDireccion() {
        return direccion;
    }
    // Método para obtener la dirección de envío
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    // Método para obtener el estado del envío
    public String getEstado() {
        return estado;
    }
    // Método para establecer el estado del envío
    public void setEstado(String estado) {
        this.estado = estado;
    }
    // Método para obtener la fecha y hora de envío
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    // Método para establecer la fecha y hora de envío
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}