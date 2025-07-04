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

/**
 *
 * @author USER
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

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
    

    @PutMapping("/Cita/{id}")
    public ResponseEntity<Cita> update(@PathVariable("id") Long id, @RequestBody Cita entidad) {
        Cita _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setFecha(entidad.getFecha());
            _entidad.setHora(entidad.getHora());
            _entidad.setEstado(entidad.getEstado());
            _entidad.setObservaciones(entidad.getObservaciones());
            _entidad.setVeterinario(entidad.getVeterinario());
            _entidad.setMascota(entidad.getMascota());
            _entidad.setDueno(entidad.getDueno());
            
            // Actualizar o crear el detalle de cita
            if (entidad.getDetalleCita() != null && entidad.getDetalleCita().getMotivo_cita() != null) {
                // Buscar si ya existe un detalle para esta cita
                Detalle_cita detalleExistente = detalleCitaRepository.findByCita(_entidad);
                
                if (detalleExistente != null) {
                    // Actualizar el detalle existente
                    Motivo_cita motivoCita = motivoCitaRepository.findById(
                        entidad.getDetalleCita().getMotivo_cita().getId()
                    ).orElse(null);
                    
                    if (motivoCita != null) {
                        detalleExistente.setMotivo_cita(motivoCita);
                        detalleCitaRepository.save(detalleExistente);
                    }
                } else {
                    // Crear nuevo detalle
                    Motivo_cita motivoCita = motivoCitaRepository.findById(
                        entidad.getDetalleCita().getMotivo_cita().getId()
                    ).orElse(null);
                    
                    if (motivoCita != null) {
                        Detalle_cita detalleCita = new Detalle_cita();
                        detalleCita.setCita(_entidad);
                        detalleCita.setMotivo_cita(motivoCita);
                        detalleCita.setEstado("Pendiente");
                        
                        detalleCitaRepository.save(detalleCita);
                    }
                }
            }
            
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Cita/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            System.out.println("Intentando eliminar cita con ID: " + id);
            
            // Obtener la cita primero
            Cita cita = repository.findById(id).orElse(null);
            if (cita == null) {
                System.out.println("Cita no encontrada con ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Primero eliminar el detalle de cita si existe
            Detalle_cita detalleCita = detalleCitaRepository.findByCita(cita);
            if (detalleCita != null) {
                System.out.println("Eliminando detalle de cita con ID: " + detalleCita.getId());
                detalleCitaRepository.delete(detalleCita);
            } else {
                System.out.println("No se encontró detalle de cita para eliminar");
            }
            
            // Luego eliminar la cita
            System.out.println("Eliminando cita con ID: " + id);
            repository.deleteById(id);
            System.out.println("Cita eliminada exitosamente");
            
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

