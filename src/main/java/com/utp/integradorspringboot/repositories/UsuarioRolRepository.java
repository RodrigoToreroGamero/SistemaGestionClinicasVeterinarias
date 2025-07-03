package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Usuario_rol;

@Repository
public interface UsuarioRolRepository extends JpaRepository<Usuario_rol, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 