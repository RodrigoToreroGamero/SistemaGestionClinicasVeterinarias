package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}