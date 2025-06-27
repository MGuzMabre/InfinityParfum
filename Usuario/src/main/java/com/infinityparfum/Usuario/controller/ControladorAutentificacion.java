package com.infinityparfum.Usuario.controller;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.Seguridad.JwtUtil;
import com.infinityparfum.Usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Autenticación", description = "Login y generación de token JWT para usuarios")
@RestController
@RequestMapping("/auth")
public class ControladorAutentificacion {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Operation(
        summary = "Iniciar sesión y obtener token JWT",
        description = "Envía correo y contraseña en el body"
    )
    @ApiResponse(responseCode = "200", description = "Login exitoso, token JWT generado")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        System.out.println("Intento de login con datos: " + loginData);

        String correo = loginData.get("correo");
        String contraseña = loginData.get("contraseña");

        try {
            Usuario usuario = usuarioService.buscarPorCorreo(correo);
            System.out.println("Usuario encontrado: " + usuario);

            if (usuario != null) {
                System.out.println("Hash guardado: " + usuario.getContraseña());
                boolean passwordMatch = passwordEncoder.matches(contraseña, usuario.getContraseña());
                System.out.println("¿Password coincide?: " + passwordMatch);

                if (passwordMatch) {
                    String token = jwtUtil.generarToken(correo);
                    System.out.println("Login exitoso, token generado.");
                    return ResponseEntity.ok(Map.of("token", token));
                } else {
                    System.out.println("Contraseña incorrecta.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
                }
            } else {
                System.out.println("Usuario no encontrado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
            }

        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("Usuario no encontrado")) {
                System.out.println("Usuario no encontrado (excepción).");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
            }
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en autenticación.");
        }
    }
}