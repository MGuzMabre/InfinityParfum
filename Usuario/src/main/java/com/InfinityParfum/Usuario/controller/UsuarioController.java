
package com.InfinityParfum.Usuario.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InfinityParfum.Usuario.model.Usuario;
import com.InfinityParfum.Usuario.service.UsuarioService;

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

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    return usuarioService.actualizarUsuario(id, usuario);
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }}
