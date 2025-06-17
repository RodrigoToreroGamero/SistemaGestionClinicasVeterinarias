/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author USER
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class GestionCitaController {
    @Autowired
    GestionCitaRepository repository;

    @GetMapping("/Cita")
    public ResponseEntity<List<Cita>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Cita> lista = new ArrayList<Cita>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Cita/{id}")
    public ResponseEntity<Cita> getById(@PathVariable("id") Long id) {
        Optional<Cita> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Cita")
    public ResponseEntity<Cita> create(@RequestBody Cita entidad) {
        try {
            Cita _entidad = repository.save(new Cita(null, entidad.getFecha(), entidad.getHora(), entidad.getEstado(), entidad.getId_mascota(), entidad.getId_veterinario()));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/Cita/{id}")
    public ResponseEntity<Cita> update(@PathVariable("id") Long id, @RequestBody Cita entidad) {
        Cita _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setFecha(entidad.getFecha());
            _entidad.setHora(entidad.getHora());
            _entidad.setEstado(entidad.getEstado());
            _entidad.setId_mascota(entidad.getId_mascota());
            _entidad.setId_veterinario(entidad.getId_veterinario());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Cita/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

