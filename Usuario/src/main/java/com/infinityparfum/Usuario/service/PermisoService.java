package com.infinityparfum.Usuario.service;

import com.infinityparfum.Usuario.model.Permiso;
import com.infinityparfum.Usuario.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> obtenerTodos() {
        return permisoRepository.findAll();
    }

    public Permiso crearPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public Permiso obtenerPorNombre(String nombre) {
        return permisoRepository.findByNombre(nombre);
    }
}
