package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Permiso;
import com.infinityparfum.Usuario.repository.PermisoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermisoServiceTest {

    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoService permisoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        Permiso permiso = new Permiso();
        when(permisoRepository.findAll()).thenReturn(List.of(permiso));
        List<Permiso> resultado = permisoService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(permisoRepository).findAll();
    }

    @Test
    void testCrearPermiso() {
        Permiso permiso = new Permiso();
        when(permisoRepository.save(permiso)).thenReturn(permiso);
        Permiso resultado = permisoService.crearPermiso(permiso);
        assertEquals(permiso, resultado);
        verify(permisoRepository).save(permiso);
    }

    @Test
    void testObtenerPorNombre() {
        Permiso permiso = new Permiso();
        when(permisoRepository.findByNombre("PERMISO")).thenReturn(permiso);
        Permiso resultado = permisoService.obtenerPorNombre("PERMISO");
        assertEquals(permiso, resultado);
        verify(permisoRepository).findByNombre("PERMISO");
    }
}