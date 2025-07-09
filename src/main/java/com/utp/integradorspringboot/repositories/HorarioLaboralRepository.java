package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Horario_laboral;

@Repository
public interface HorarioLaboralRepository extends JpaRepository<Horario_laboral, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 