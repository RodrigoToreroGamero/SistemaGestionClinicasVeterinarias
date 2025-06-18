package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    // Puedes agregar métodos personalizados si lo necesitas, por ejemplo:
    // List<Mascota> findByUsuarioId(Long idUsuario);
    List<Mascota> findByUsuarioId(Long usuarioId);

}
