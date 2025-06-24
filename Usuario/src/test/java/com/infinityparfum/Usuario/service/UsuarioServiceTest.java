package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Rol;
import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.repository.RolRepository;
import com.infinityparfum.Usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        List<Usuario> resultado = usuarioService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testAgregarUsuarioConRolCliente() {
        Usuario usuario = new Usuario();
        usuario.setContraseña("12345678");
        Rol rolCliente = new Rol(1, "CLIENTE");

        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rolCliente));
        when(passwordEncoder.encode("12345678")).thenReturn("encoded");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.agregarUsuario(usuario);

        assertTrue(resultado.getRoles().contains(rolCliente));
        assertEquals("encoded", resultado.getContraseña());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testAgregarUsuarioSinRolCliente() {
        Usuario usuario = new Usuario();
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> usuarioService.agregarUsuario(usuario));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testBuscarPorIdExiste() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario resultado = usuarioService.buscarPorId(1L);
        assertEquals(usuario, resultado);
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> usuarioService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorCorreoExiste() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByCorreo("correo@test.com")).thenReturn(Optional.of(usuario));
        Usuario resultado = usuarioService.buscarPorCorreo("correo@test.com");
        assertEquals(usuario, resultado);
        verify(usuarioRepository).findByCorreo("correo@test.com");
    }

    @Test
    void testBuscarPorCorreoNoExiste() {
        when(usuarioRepository.findByCorreo("correo@test.com")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> usuarioService.buscarPorCorreo("correo@test.com"));
    }

    @Test
    void testActualizarUsuario() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setContraseña("oldpass");
        usuarioExistente.setActivo(true);

        Usuario datos = new Usuario();
        datos.setNombre("Nuevo");
        datos.setCorreo("nuevo@correo.com");
        datos.setActivo(false);
        datos.setContraseña("nueva");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.encode("nueva")).thenReturn("encoded");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.actualizarUsuario(1L, datos);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("nuevo@correo.com", resultado.getCorreo());
        assertEquals("encoded", resultado.getContraseña());
        assertFalse(resultado.isActivo());
    }

    @Test
    void testAsignarRolExiste() {
        Usuario usuario = new Usuario();
        Rol rol = new Rol(2, "ADMIN");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.asignarRol(1L, "ADMIN");

        assertTrue(resultado.getRoles().contains(rol));
    }

    @Test
    void testAsignarRolNoExiste() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.asignarRol(1L, "ADMIN"));
    }

    @Test
    void testDesactivarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setActivo(true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.desactivarUsuario(1L);

        assertFalse(resultado.isActivo());
    }

    @Test
    void testExisteUsuario() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        assertTrue(usuarioService.existeUsuario(1L));
        verify(usuarioRepository).existsById(1L);
    }

    @Test
    void testEliminarPorIdExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.eliminarPorId(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testEliminarPorIdNoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> usuarioService.eliminarPorId(1L));
    }
}