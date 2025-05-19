package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuarioExistente = buscarPorId(id);
        usuarioExistente.setNombre(datosActualizados.getNombre());
        usuarioExistente.setCorreo(datosActualizados.getCorreo());
        usuarioExistente.setActivo(datosActualizados.isActivo());
        return usuarioRepository.save(usuarioExistente);
            }

    public Usuario asignarRol(Long id, String nombreRol) {
        Usuario usuario = buscarPorId(id);
        // Lógica para asignar un rol al usuario (implementar según tu modelo de roles)
        return usuario;
    }

    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }

    public void eliminarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Nuevo método para validar la existencia de un usuario
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }
}