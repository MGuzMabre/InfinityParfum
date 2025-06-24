package com.infinityparfum.Usuario.controller;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol CLIENTE no encontrado")
    })
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.agregarUsuario(usuario);
    }

    @Operation(summary = "Crear múltiples usuarios")
    @PostMapping("/lista")
    public List<Usuario> crearUsuarios(@RequestBody List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuarioService::agregarUsuario)
                .toList();
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @Operation(summary = "Actualizar un usuario existente")
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @Operation(summary = "Asignar un rol a un usuario")
    @PostMapping("/{id}/rol")
    public Usuario asignarRol(@PathVariable Long id, @RequestParam String nombreRol) {
        return usuarioService.asignarRol(id, nombreRol);
    }

    @Operation(summary = "Desactivar un usuario (estado activo = false)")
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
    }

    @Operation(summary = "Verificar si un usuario existe por ID")
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> validarExistenciaUsuario(@PathVariable Long id) {
        boolean existe = usuarioService.existeUsuario(id);
        return ResponseEntity.ok(existe);
    }
}
