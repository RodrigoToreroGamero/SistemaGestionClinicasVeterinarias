package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Dueno;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 
