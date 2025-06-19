package com.infinityparfum.Usuario.controller;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Retorna todos los usuarios del sistema")
    @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente")
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con rol CLIENTE por defecto")
    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente")
    @PostMapping
    public Usuario crearUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"nombre\": \"Juan\", \"correo\": \"juan@example.com\", \"contraseña\": \"12345678\" }")
                    )
            )
            @RequestBody Usuario usuario) {
        return usuarioService.agregarUsuario(usuario);
    }

    @Operation(summary = "Crear múltiples usuarios", description = "Permite registrar varios usuarios de una vez")
    @PostMapping("/lista")
    public List<Usuario> crearUsuarios(@RequestBody List<Usuario> usuarios) {
        List<Usuario> agregados = new ArrayList<>();
        for (Usuario u : usuarios) {
            agregados.add(usuarioService.agregarUsuario(u));
        }
        return agregados;
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente")
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @Operation(summary = "Asignar rol a usuario", description = "Asigna un nuevo rol a un usuario")
    @PostMapping("/{id}/rol")
    public Usuario asignarRol(
            @PathVariable Long id,
            @Parameter(description = "Nombre del rol (ej: ADMIN, CLIENTE)")
            @RequestParam String nombreRol) {
        return usuarioService.asignarRol(id, nombreRol);
    }

    @Operation(summary = "Desactivar usuario", description = "Marca un usuario como inactivo")
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina permanentemente un usuario del sistema")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
    }

    @Operation(summary = "Validar existencia de usuario", description = "Verifica si un usuario existe según su ID")
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> validarExistenciaUsuario(@PathVariable Long id) {
        boolean existe = usuarioService.existeUsuario(id);
        return ResponseEntity.ok(existe);
    }
}
