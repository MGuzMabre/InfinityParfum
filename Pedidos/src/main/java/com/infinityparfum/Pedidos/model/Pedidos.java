package com.infinityparfum.Pedidos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Entidad que representa un pedido de cliente",
    example = "{\n" +
            "  \"clienteId\": 5,\n" +
            "  \"estado\": \"Pendiente\",\n" +
            "  \"items\": [\n" +
            "    { \"productoId\": 10, \"cantidad\": 2, \"precioUnitario\": 15000 },\n" +
            "    { \"productoId\": 12, \"cantidad\": 1, \"precioUnitario\": 25000 }\n" +
            "  ]\n" +
            "}"
)
@Entity
@Table(name = "pedidos")
public class Pedidos {

    @Schema(description = "ID del pedido", example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del cliente que realiza el pedido", example = "5")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Schema(description = "Total del pedido", example = "39980")
    @Column(nullable = false)
    private Double total;

    @Schema(description = "Estado del pedido", example = "Pendiente")
    @Column(nullable = false)
    private String estado;

    @Schema(description = "ID del pago asociado", example = "3")
    @Column(name = "pago_id")
    private Long pagoId;

    @Schema(description = "ID del envío asociado", example = "2")
    @Column(name = "envio_id")
    private Long envioId;

    @Schema(description = "Fecha de realización del pedido")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha = LocalDateTime.now();

    @Schema(description = "Lista de productos en el pedido")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> items = new ArrayList<>();


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public Long getEnvioId() {
        return envioId;
    }

    public void setEnvioId(Long envioId) {
        this.envioId = envioId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }
}
