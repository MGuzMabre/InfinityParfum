package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Rol;
import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.repository.RolRepository;
import com.infinityparfum.Usuario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Agregar un nuevo usuario con rol CLIENTE por defecto
    public Usuario agregarUsuario(Usuario usuario) {
        Rol rolCliente = rolRepository.findByNombre("CLIENTE");
        if (rolCliente == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "El rol CLIENTE no está definido.");
        }

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            usuario.setRoles(new HashSet<>());
        }

        usuario.getRoles().add(rolCliente);
        return usuarioRepository.save(usuario);
    }

    // Buscar un usuario por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    // Actualizar datos del usuario
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuario = buscarPorId(id);
        usuario.setNombre(datosActualizados.getNombre());
        usuario.setCorreo(datosActualizados.getCorreo());
        usuario.setActivo(datosActualizados.isActivo());
        return usuarioRepository.save(usuario);
    }

    // Asignar un rol a un usuario
    public Usuario asignarRol(Long id, String nombreRol) {
        Usuario usuario = buscarPorId(id);
        Rol rol = rolRepository.findByNombre(nombreRol);
        if (rol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no encontrado: " + nombreRol);
        }
        usuario.getRoles().add(rol);
        return usuarioRepository.save(usuario);
    }

    // Desactivar usuario (activo -> false)
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario por ID
    public void eliminarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Verificar si un usuario existe
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }
}
