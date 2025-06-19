package com.infinityparfum.Usuario.repository;

import com.infinityparfum.Usuario.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    Permiso findByNombre(String nombre);
}
