package com.utp.integradorspringboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    Optional<Sesion> findByCorreo(String correo);
    Optional<Sesion> findByCorreoIgnoreCase(String correo);
    Optional<Sesion> findByUsuario_Id(Long usuarioId);
} 

} 