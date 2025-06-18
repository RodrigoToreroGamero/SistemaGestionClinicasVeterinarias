package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Mascota;
import com.utp.integradorspringboot.repositories.MascotaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class GestionMascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping("/Mascota")
    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }
}
