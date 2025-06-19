package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Rol;
import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.repository.RolRepository;
import com.infinityparfum.Usuario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Agregar un nuevo usuario con rol CLIENTE por defecto
    public Usuario agregarUsuario(Usuario usuario) {
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol CLIENTE no encontrado"));

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            usuario.setRoles(new HashSet<>());
        }

        usuario.getRoles().add(rolCliente);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }

    // Buscar un usuario por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    // Buscar por correo
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ese correo"));
    }

    // Actualizar datos del usuario
    public Usuario actualizarUsuario(Long id, Usuario datos) {
        Usuario usuario = buscarPorId(id);

        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());
        usuario.setActivo(datos.isActivo());

        if (datos.getContraseña() != null && !datos.getContraseña().isBlank()) {
            usuario.setContraseña(passwordEncoder.encode(datos.getContraseña()));
        }

        return usuarioRepository.save(usuario);
    }

    // Asignar un rol a un usuario
    public Usuario asignarRol(Long usuarioId, String nombreRol) {
        Usuario usuario = buscarPorId(usuarioId);
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no encontrado: " + nombreRol));

        usuario.getRoles().add(rol);
        return usuarioRepository.save(usuario);
    }

    // Desactivar usuario
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }

    // Verificar si un usuario existe
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    // Eliminar usuario por ID
    public void eliminarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
