package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Detalle_cita;

@Repository
public interface DetalleCitaRepository extends JpaRepository<Detalle_cita, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
    Detalle_cita findByCita(Cita cita);
} 