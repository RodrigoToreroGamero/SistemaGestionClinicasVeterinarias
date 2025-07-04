package com.utp.integradorspringboot.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.integradorspringboot.models.Clinica;
import com.utp.integradorspringboot.repositories.ClinicaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ClinicaController {

    @Autowired
    ClinicaRepository clinicaRepository;

    // Obtener todas las clínicas
    @GetMapping("/Clinica")
    public ResponseEntity<List<Clinica>> getAll() {
        try {
            List<Clinica> lista = new ArrayList<>();
            clinicaRepository.findAll().forEach(lista::add);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener clínica por ID
    @GetMapping("/Clinica/{id}")
    public ResponseEntity<Clinica> getById(@PathVariable("id") Long id) {
        Optional<Clinica> clinicaData = clinicaRepository.findById(id);

        if (clinicaData.isPresent()) {
            return new ResponseEntity<>(clinicaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear nueva clínica
    @PostMapping("/Clinica")
    public ResponseEntity<Clinica> create(@RequestBody Clinica clinica) {
        try {
            Clinica nuevaClinica = clinicaRepository.save(clinica);
            return new ResponseEntity<>(nuevaClinica, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar clínica
    @PutMapping("/Clinica/{id}")
    public ResponseEntity<Clinica> update(@PathVariable("id") Long id, @RequestBody Clinica clinica) {
        Optional<Clinica> clinicaData = clinicaRepository.findById(id);

        if (clinicaData.isPresent()) {
            Clinica _clinica = clinicaData.get();
            _clinica.setNombre_clinica(clinica.getNombre_clinica());
            _clinica.setRuc(clinica.getRuc());
            _clinica.setDireccion(clinica.getDireccion());
            _clinica.setTelefono(clinica.getTelefono());
            _clinica.setLink_web(clinica.getLink_web());
            _clinica.setPlan_suscripcion(clinica.getPlan_suscripcion());
            _clinica.setPasarela_pago(clinica.getPasarela_pago());

            return new ResponseEntity<>(clinicaRepository.save(_clinica), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar clínica
    @DeleteMapping("/Clinica/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            clinicaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 