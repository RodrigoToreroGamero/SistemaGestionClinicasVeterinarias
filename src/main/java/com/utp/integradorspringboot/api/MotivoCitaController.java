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

import com.utp.integradorspringboot.models.Motivo_cita;
import com.utp.integradorspringboot.repositories.MotivoCitaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MotivoCitaController {

    @Autowired
    MotivoCitaRepository motivoCitaRepository;

    // Obtener todos los motivos de cita
    @GetMapping("/MotivoCita")
    public ResponseEntity<List<Motivo_cita>> getAll() {
        try {
            List<Motivo_cita> lista = new ArrayList<>();
            motivoCitaRepository.findAll().forEach(lista::add);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener motivo de cita por ID
    @GetMapping("/MotivoCita/{id}")
    public ResponseEntity<Motivo_cita> getById(@PathVariable("id") Long id) {
        Optional<Motivo_cita> motivoCitaData = motivoCitaRepository.findById(id);

        if (motivoCitaData.isPresent()) {
            return new ResponseEntity<>(motivoCitaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear nuevo motivo de cita
    @PostMapping("/MotivoCita")
    public ResponseEntity<Motivo_cita> create(@RequestBody Motivo_cita motivoCita) {
        try {
            // Validar campos requeridos
            if (motivoCita.getNombre() == null || motivoCita.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            if (motivoCita.getPrecio() == null || motivoCita.getPrecio() <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Motivo_cita nuevoMotivoCita = motivoCitaRepository.save(motivoCita);
            return new ResponseEntity<>(nuevoMotivoCita, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar motivo de cita
    @PutMapping("/MotivoCita/{id}")
    public ResponseEntity<Motivo_cita> update(@PathVariable("id") Long id, @RequestBody Motivo_cita motivoCita) {
        Optional<Motivo_cita> motivoCitaData = motivoCitaRepository.findById(id);

        if (motivoCitaData.isPresent()) {
            Motivo_cita _motivoCita = motivoCitaData.get();
            _motivoCita.setNombre(motivoCita.getNombre());
            _motivoCita.setPrecio(motivoCita.getPrecio());

            return new ResponseEntity<>(motivoCitaRepository.save(_motivoCita), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar motivo de cita
    @DeleteMapping("/MotivoCita/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            motivoCitaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 