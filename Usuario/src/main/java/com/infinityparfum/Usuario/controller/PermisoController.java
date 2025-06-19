package com.infinityparfum.Usuario.controller;

import com.infinityparfum.Usuario.model.Permiso;
import com.infinityparfum.Usuario.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permisos", description = "Operaciones CRUD sobre permisos")
@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @Operation(summary = "Listar permisos", description = "Devuelve una lista de todos los permisos registrados")
    @ApiResponse(responseCode = "200", description = "Permisos listados correctamente")
    @GetMapping
    public List<Permiso> listarPermisos() {
        return permisoService.obtenerTodos();
    }

    @Operation(summary = "Crear un permiso", description = "Registra un nuevo permiso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public Permiso crearPermiso(
            @RequestBody(description = "Permiso a registrar", required = true, content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"nombre\": \"CREAR_USUARIO\", \"descripcion\": \"Permite crear usuarios nuevos\" }"))) @org.springframework.web.bind.annotation.RequestBody Permiso permiso) {
        return permisoService.crearPermiso(permiso);
    }
}
