package com.infinityparfum.Usuario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.infinityparfum.Usuario.model.Usuario;

import org.springframework.http.HttpStatus;

@Service
public class UsuarioService {


    
    private List<Usuario> listaUsuarios = new ArrayList<>();

    public UsuarioService() {
        
        listaUsuarios.add(new Usuario(1L, "Danilo Celis", "danilo@infinity.com"));
        listaUsuarios.add(new Usuario(2L, "Matías Guzmán", "matias@infinity.com"));
        listaUsuarios.add(new Usuario(3L, "Felipe Quezada", "felipe@infinity.com"));
    }

    public List<Usuario> obtenerTodos() {
        return listaUsuarios;
    }

    public Usuario agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        return usuario;
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(id)) {
                u.setNombre(datosActualizados.getNombre());
                u.setCorreo(datosActualizados.getCorreo());
                return u;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
    }
    
    public void eliminarPorId(Long id) {
        Usuario usuarioAEliminar = null;

        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(id)) {
                usuarioAEliminar = u;
                break;
            }
        }

        if (usuarioAEliminar != null) {
            listaUsuarios.remove(usuarioAEliminar);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }
}