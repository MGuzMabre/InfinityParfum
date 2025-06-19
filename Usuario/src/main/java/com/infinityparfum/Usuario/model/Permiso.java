package com.infinityparfum.Usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "Permiso asignable a roles (ej: VER_USUARIOS, CREAR_USUARIO)")
@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre del permiso", example = "CREAR_USUARIO")
    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite crear usuarios en el sistema")
    @Size(max = 255)
    private String descripcion;

    @ManyToMany(mappedBy = "permisos")
    @JsonIgnoreProperties("permisos")
    private Set<Rol> roles = new HashSet<>();

    public Permiso() {}

    public Permiso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Set<Rol> getRoles() { return roles; }
    public void setRoles(Set<Rol> roles) { this.roles = roles; }
}
