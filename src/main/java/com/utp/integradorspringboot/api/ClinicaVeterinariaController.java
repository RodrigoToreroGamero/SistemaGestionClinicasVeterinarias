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
    public ResponseEntity<ClinicaVeterinaria> create(@RequestBody ClinicaVeterinaria clinica, @RequestBody Veterinario vet) {
        try {
            
            
            ClinicaVeterinaria _entidad = repository.save(new ClinicaVeterinaria(
                    clinica.getNombre_clinica(),
                    clinica.getRuc(),
                    clinica.getDireccion_sede(),
                    clinica.getLink_web(),
                    clinica.getTelefono_sede(),
                    vet
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
