package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Veterinario_horario;

@Repository
public interface VeterinarioHorarioRepository extends JpaRepository<Veterinario_horario, Long> {
    // Las operaciones CRUD básicas son proporcionadas automáticamente por JpaRepository
} 