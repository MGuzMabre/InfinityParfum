package com.infinityparfum.Usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "Entidad que representa un usuario del sistema")
@Entity
@Table(name = "usuario")
public class Usuario {

    @Schema(description = "ID del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com")
    @NotNull
    @Email
    private String correo;

    @Schema(description = "Contraseña del usuario", example = "12345678")
    @NotNull
    @Size(min = 8)
    private String contraseña;

    @Schema(description = "Indica si el usuario está activo", example = "true")
    private boolean activo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonIgnoreProperties("usuarios")
    @Schema(description = "Roles asignados al usuario")
    private Set<Rol> roles = new HashSet<>();

    // Getters y Setters

    public Usuario() {}

    public Usuario(Long id, String nombre, String correo, String contraseña, boolean activo, Set<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.activo = activo;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Set<Rol> getRoles() { return roles; }
    public void setRoles(Set<Rol> roles) { this.roles = roles; }
}
