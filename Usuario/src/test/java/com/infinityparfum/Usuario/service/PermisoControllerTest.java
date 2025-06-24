package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.controller.PermisoController;
import com.infinityparfum.Usuario.model.Permiso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermisoControllerTest {

    @Mock
    private PermisoService permisoService;

    @InjectMocks
    private PermisoController permisoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPermisos() {
        Permiso permiso = new Permiso();
        when(permisoService.obtenerTodos()).thenReturn(List.of(permiso));
        List<Permiso> resultado = permisoController.listarPermisos();
        assertEquals(1, resultado.size());
        verify(permisoService).obtenerTodos();
    }

    @Test
    void testCrearPermiso() {
        Permiso permiso = new Permiso();
        when(permisoService.crearPermiso(permiso)).thenReturn(permiso);
        Permiso resultado = permisoController.crearPermiso(permiso);
        assertEquals(permiso, resultado);
        verify(permisoService).crearPermiso(permiso);
    }
}