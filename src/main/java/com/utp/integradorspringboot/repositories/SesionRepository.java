package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 