package com.utp.integradorspringboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.integradorspringboot.models.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    // Puedes agregar métodos personalizados si lo necesitas, por ejemplo:
    List<Mascota> findByDuenoId(Long duenoId);

}
