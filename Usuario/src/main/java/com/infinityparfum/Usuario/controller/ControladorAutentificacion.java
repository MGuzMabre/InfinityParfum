package com.infinityparfum.Usuario.controller;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.Seguridad.JwtUtil;
import com.infinityparfum.Usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @Operation(summary = "Iniciar sesión y obtener token JWT", 
        requestBody = @RequestBody(
            required = true,
            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                value = "{ \"correo\": \"usuario@example.com\", \"contraseña\": \"12345678\" }"
            ))
        )
    )
    @ApiResponse(responseCode = "200", description = "Login exitoso, token JWT generado")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String contraseña = loginData.get("contraseña");

        try {
            Usuario usuario = usuarioService.buscarPorCorreo(correo);

            if (usuario != null && passwordEncoder.matches(contraseña, usuario.getContraseña())) {
                String token = jwtUtil.generarToken(correo);
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }
    }
}
