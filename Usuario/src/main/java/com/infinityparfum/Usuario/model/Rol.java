package com.infinityparfum.Usuario.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Rol que puede tener un usuario (ej: ADMIN, CLIENTE)")
@Entity
@Table(name = "roles")
public class Rol {
    @Schema(description = "ID del rol", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre del rol", example = "CLIENTE")
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private Set<Usuario> usuarios = new HashSet<>();

    public Rol() {}

    public Rol(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
