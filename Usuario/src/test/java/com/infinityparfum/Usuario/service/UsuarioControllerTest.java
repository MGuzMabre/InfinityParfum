package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.controller.UsuarioController;
import com.infinityparfum.Usuario.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarUsuarios() {
        Usuario usuario = new Usuario();
        when(usuarioService.obtenerTodos()).thenReturn(List.of(usuario));
        List<Usuario> resultado = usuarioController.listarUsuarios();
        assertEquals(1, resultado.size());
        verify(usuarioService).obtenerTodos();
    }

    @Test
    void testCrearUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioService.agregarUsuario(usuario)).thenReturn(usuario);
        Usuario resultado = usuarioController.crearUsuario(usuario);
        assertEquals(usuario, resultado);
        verify(usuarioService).agregarUsuario(usuario);
    }

    @Test
    void testCrearUsuarios() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioService.agregarUsuario(usuario1)).thenReturn(usuario1);
        when(usuarioService.agregarUsuario(usuario2)).thenReturn(usuario2);

        List<Usuario> resultado = usuarioController.crearUsuarios(usuarios);

        assertEquals(2, resultado.size());
        verify(usuarioService).agregarUsuario(usuario1);
        verify(usuarioService).agregarUsuario(usuario2);
    }

    @Test
    void testObtenerUsuarioPorId() {
        Usuario usuario = new Usuario();
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        Usuario resultado = usuarioController.obtenerUsuarioPorId(1L);
        assertEquals(usuario, resultado);
        verify(usuarioService).buscarPorId(1L);
    }

    @Test
    void testActualizarUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioService.actualizarUsuario(1L, usuario)).thenReturn(usuario);
        Usuario resultado = usuarioController.actualizarUsuario(1L, usuario);
        assertEquals(usuario, resultado);
        verify(usuarioService).actualizarUsuario(1L, usuario);
    }

    @Test
    void testAsignarRol() {
        Usuario usuario = new Usuario();
        when(usuarioService.asignarRol(1L, "ADMIN")).thenReturn(usuario);
        Usuario resultado = usuarioController.asignarRol(1L, "ADMIN");
        assertEquals(usuario, resultado);
        verify(usuarioService).asignarRol(1L, "ADMIN");
    }

    @Test
    void testDesactivarUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioService.desactivarUsuario(1L)).thenReturn(usuario);
        ResponseEntity<Usuario> response = usuarioController.desactivarUsuario(1L);
        assertEquals(usuario, response.getBody());
        verify(usuarioService).desactivarUsuario(1L);
    }

    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioService).eliminarPorId(1L);
        usuarioController.eliminarUsuario(1L);
        verify(usuarioService).eliminarPorId(1L);
    }

    @Test
    void testValidarExistenciaUsuario() {
        when(usuarioService.existeUsuario(1L)).thenReturn(true);
        ResponseEntity<Boolean> response = usuarioController.validarExistenciaUsuario(1L);
        assertTrue(response.getBody());
        verify(usuarioService).existeUsuario(1L);
    }
}