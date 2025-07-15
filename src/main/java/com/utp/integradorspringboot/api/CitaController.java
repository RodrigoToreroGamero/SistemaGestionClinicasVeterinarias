/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Detalle_cita;
import com.utp.integradorspringboot.models.Motivo_cita;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
import com.utp.integradorspringboot.repositories.DetalleCitaRepository;
import com.utp.integradorspringboot.repositories.MotivoCitaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 *
 * @author USER
 */
@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("")
public class CitaController {
    @Autowired
    GestionCitaRepository repository;
    
    @Autowired
    DetalleCitaRepository detalleCitaRepository;
    
    @Autowired
    MotivoCitaRepository motivoCitaRepository;

    @GetMapping("/Cita")
    public ResponseEntity<List<Cita>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Cita> lista = new ArrayList<Cita>();
            repository.findAll().forEach(lista::add);
            
            System.out.println("Total citas encontradas: " + lista.size());
            for (Cita cita : lista) {
                System.out.println("Cita ID: " + cita.getId() + 
                                 ", Fecha: " + cita.getFecha() + 
                                 ", DetalleCita: " + (cita.getDetalleCita() != null ? "Sí" : "No"));
            }
            
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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
            // Crear la cita
            Cita _entidad = repository.save(new Cita(
                null, 
                entidad.getFecha(),
                entidad.getHora(), 
                entidad.getEstado(),
                entidad.getObservaciones() != null ? entidad.getObservaciones() : "",
                entidad.getMascota(),
                entidad.getVeterinario(),
                entidad.getDueno()));
            
            // Si hay detalle de cita, crearlo también
            if (entidad.getDetalleCita() != null && entidad.getDetalleCita().getMotivo_cita() != null) {
                // Obtener el motivo de cita
                Motivo_cita motivoCita = motivoCitaRepository.findById(
                    entidad.getDetalleCita().getMotivo_cita().getId()
                ).orElse(null);
                
                if (motivoCita != null) {
                    // Crear el detalle de cita
                    Detalle_cita detalleCita = new Detalle_cita();
                    detalleCita.setCita(_entidad);
                    detalleCita.setMotivo_cita(motivoCita);
                    detalleCita.setEstado("Pendiente");
                    
                    detalleCitaRepository.save(detalleCita);
                }
            }
            
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

