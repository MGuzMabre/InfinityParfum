package com.infinityparfum.Usuario.service;
import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.model.Rol;
import com.infinityparfum.Usuario.repository.UsuarioRepository;
import com.infinityparfum.Usuario.repository.RolRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario asignarRol(Long usuarioId, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Rol rol = rolRepository.findByNombre(nombreRol);
        if (rol == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }

        usuario.getRoles().add(rol);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuarioExistente.setNombre(datosActualizados.getNombre());
        usuarioExistente.setCorreo(datosActualizados.getCorreo());
        usuarioExistente.setContraseña(datosActualizados.getContraseña());

        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public void eliminarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
