package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.ClinicaVeterinaria;
import com.utp.integradorspringboot.models.Veterinario;
import com.utp.integradorspringboot.repositories.ClinicaVeterinariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ClinicaVeterinariaController {

    @Autowired
    ClinicaVeterinariaRepository repository;

    
    @PostMapping("/clinicaVeterinaria")
    public ResponseEntity<ClinicaVeterinaria> create(@RequestBody ClinicaVeterinaria entidad) {
        try {
            
            Veterinario vet = new Veterinario();
            ClinicaVeterinaria _entidad = repository.save(new ClinicaVeterinaria(
                    entidad.getNombre_clinica(),
                    entidad.getRuc(),
                    entidad.getDireccion_sede(),
                    entidad.getLink_web(),
                    entidad.getTelefono_sede(),
                    entidad.getVeterinario_responsable()
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
