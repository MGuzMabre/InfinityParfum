package com.infinityparfum.Usuario.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.agregarUsuario(usuario);
    }

    @PostMapping("/lista")
    public List<Usuario> crearUsuarios(@RequestBody List<Usuario> usuarios) {
        List<Usuario> agregados = new ArrayList<>();
        for (Usuario u : usuarios) {
            agregados.add(usuarioService.agregarUsuario(u));
        }
        return agregados;
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @PostMapping("/{id}/rol")
    public Usuario asignarRol(@PathVariable Long id, @RequestParam String nombreRol) {
        return usuarioService.asignarRol(id, nombreRol);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
    }

    // Nuevo endpoint para validar la existencia de un usuario
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> validarExistenciaUsuario(@PathVariable Long id) {
        boolean existe = usuarioService.existeUsuario(id);
        return ResponseEntity.ok(existe);
    }
}