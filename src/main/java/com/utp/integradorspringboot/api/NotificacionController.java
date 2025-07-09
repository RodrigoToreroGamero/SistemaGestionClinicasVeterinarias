package com.utp.integradorspringboot.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.utp.integradorspringboot.models.Notificacion;
import com.utp.integradorspringboot.repositories.NotificacionRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class NotificacionController {
    @Autowired
    NotificacionRepository notificacionRepository;

    @GetMapping("/Notificacion")
    public ResponseEntity<List<Notificacion>> getAll() {
        try {
            List<Notificacion> lista = new ArrayList<>();
            notificacionRepository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Notificacion/{id}")
    public ResponseEntity<Notificacion> getById(@PathVariable("id") Long id) {
        Optional<Notificacion> entidad = notificacionRepository.findById(id);
        return entidad.map(notificacion -> new ResponseEntity<>(notificacion, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Notificacion")
    public ResponseEntity<Notificacion> create(@RequestBody Notificacion notificacion) {
        try {
            Notificacion nuevo = notificacionRepository.save(notificacion);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Notificacion/{id}")
    public ResponseEntity<Notificacion> update(@PathVariable("id") Long id, @RequestBody Notificacion notificacion) {
        Optional<Notificacion> entidad = notificacionRepository.findById(id);
        if (entidad.isPresent()) {
            Notificacion _notificacion = entidad.get();
            _notificacion.setTipo(notificacion.getTipo());
            _notificacion.setMensaje(notificacion.getMensaje());
            _notificacion.setFecha_creacion(notificacion.getFecha_creacion());
            _notificacion.setEstado(notificacion.getEstado());
            _notificacion.setCita(notificacion.getCita());
            return new ResponseEntity<>(notificacionRepository.save(_notificacion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Notificacion/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            notificacionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 