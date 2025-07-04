package com.utp.integradorspringboot.api;

import java.time.LocalDateTime;
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

import com.utp.integradorspringboot.models.Boleta_pago;
import com.utp.integradorspringboot.models.Detalle_cita;
import com.utp.integradorspringboot.repositories.BoletaPagoRepository;
import com.utp.integradorspringboot.repositories.DetalleCitaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BoletaPagoController {

    @Autowired
    BoletaPagoRepository boletaPagoRepository;

    @Autowired
    DetalleCitaRepository detalleCitaRepository;

    // Obtener todas las boletas de pago
    @GetMapping("/BoletaPago")
    public ResponseEntity<List<Boleta_pago>> getAll() {
        try {
            List<Boleta_pago> lista = new ArrayList<>();
            boletaPagoRepository.findAll().forEach(lista::add);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener boleta de pago por ID
    @GetMapping("/BoletaPago/{id}")
    public ResponseEntity<Boleta_pago> getById(@PathVariable("id") Long id) {
        Optional<Boleta_pago> boletaPagoData = boletaPagoRepository.findById(id);

        if (boletaPagoData.isPresent()) {
            return new ResponseEntity<>(boletaPagoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear nueva boleta de pago
    @PostMapping("/BoletaPago")
    public ResponseEntity<Boleta_pago> create(@RequestBody Boleta_pago boletaPago) {
        try {
            // Validar que el detalle de cita exista
            if (boletaPago.getDetalle_cita() == null || boletaPago.getDetalle_cita().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Optional<Detalle_cita> detalleCita = detalleCitaRepository.findById(boletaPago.getDetalle_cita().getId());
            if (detalleCita.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            boletaPago.setDetalle_cita(detalleCita.get());
            
            // Validar campos requeridos
            if (boletaPago.getMonto_total() == null || boletaPago.getMonto_total() <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            if (boletaPago.getMetodo_pago() == null || boletaPago.getMetodo_pago().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Establecer fecha de emisión si no se proporciona
            if (boletaPago.getFecha_emision() == null) {
                boletaPago.setFecha_emision(LocalDateTime.now());
            }

            Boleta_pago nuevaBoletaPago = boletaPagoRepository.save(boletaPago);
            return new ResponseEntity<>(nuevaBoletaPago, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar boleta de pago
    @PutMapping("/BoletaPago/{id}")
    public ResponseEntity<Boleta_pago> update(@PathVariable("id") Long id, @RequestBody Boleta_pago boletaPago) {
        Optional<Boleta_pago> boletaPagoData = boletaPagoRepository.findById(id);

        if (boletaPagoData.isPresent()) {
            Boleta_pago _boletaPago = boletaPagoData.get();
            _boletaPago.setMonto_total(boletaPago.getMonto_total());
            _boletaPago.setMetodo_pago(boletaPago.getMetodo_pago());
            _boletaPago.setFecha_emision(boletaPago.getFecha_emision());

            // Actualizar detalle de cita si se proporciona
            if (boletaPago.getDetalle_cita() != null && boletaPago.getDetalle_cita().getId() != null) {
                Optional<Detalle_cita> detalleCita = detalleCitaRepository.findById(boletaPago.getDetalle_cita().getId());
                detalleCita.ifPresent(_boletaPago::setDetalle_cita);
            }

            return new ResponseEntity<>(boletaPagoRepository.save(_boletaPago), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar boleta de pago
    @DeleteMapping("/BoletaPago/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            boletaPagoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 