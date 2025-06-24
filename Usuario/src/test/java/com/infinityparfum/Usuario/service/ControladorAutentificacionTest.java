package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.controller.ControladorAutentificacion;
import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.Seguridad.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorAutentificacionTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ControladorAutentificacion controladorAutentificacion;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExitoso() {
        String correo = "test@example.com";
        String rawPassword = "12345678";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContraseña(encodedPassword);

        when(usuarioService.buscarPorCorreo(correo)).thenReturn(usuario);
        when(jwtUtil.generarToken(correo)).thenReturn("token123");

        ResponseEntity<?> response = controladorAutentificacion.login(Map.of("correo", correo, "contraseña", rawPassword));

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("token123"));
    }

    @Test
    void testLoginCredencialesInvalidas() {
        String correo = "test@example.com";
        String rawPassword = "wrongpassword";
        String encodedPassword = passwordEncoder.encode("12345678");

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContraseña(encodedPassword);

        when(usuarioService.buscarPorCorreo(correo)).thenReturn(usuario);

        ResponseEntity<?> response = controladorAutentificacion.login(Map.of("correo", correo, "contraseña", rawPassword));

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Credenciales inválidas"));
    }

    @Test
    void testLoginUsuarioNoEncontrado() {
        String correo = "noexiste@example.com";
        when(usuarioService.buscarPorCorreo(correo)).thenThrow(new RuntimeException("Usuario no encontrado"));

        ResponseEntity<?> response = controladorAutentificacion.login(Map.of("correo", correo, "contraseña", "12345678"));

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Usuario no encontrado"));
    }
}