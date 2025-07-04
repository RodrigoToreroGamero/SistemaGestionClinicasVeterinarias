package com.utp.integradorspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Boleta_pago;

@Repository
public interface BoletaPagoRepository extends JpaRepository<Boleta_pago, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
} 