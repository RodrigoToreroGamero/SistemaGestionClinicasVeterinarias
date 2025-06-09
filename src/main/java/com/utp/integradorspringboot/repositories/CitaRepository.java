package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
}