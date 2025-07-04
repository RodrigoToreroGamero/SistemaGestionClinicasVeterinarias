package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 