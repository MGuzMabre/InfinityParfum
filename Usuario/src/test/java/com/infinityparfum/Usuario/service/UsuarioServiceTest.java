package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Usuario;
import com.infinityparfum.Usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository).findById(1L);
    }
}
