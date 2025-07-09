package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.repositories.SesionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class SesionController {
    
    @Autowired
    SesionRepository repository;
    
    @GetMapping("/Sesion")
    public ResponseEntity<List<Sesion>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Sesion> lista = new ArrayList<Sesion>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/Sesion/{id}")
    public ResponseEntity<Sesion> getById(@PathVariable("id") Long id) {
        Optional<Sesion> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Sesion")
    public ResponseEntity<Sesion> create(@RequestBody Sesion entidad) {
        try {
            Sesion _entidad = repository.save(new Sesion(
                null, 
                entidad.getCorreo(),
                entidad.getContrasena(), 
                entidad.getUsuario(),
                entidad.getFecha_creacion())
            );
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/Sesion/{id}")
    public ResponseEntity<Sesion> update(@PathVariable("id") Long id, @RequestBody Sesion entidad) {
        Sesion _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setCorreo(entidad.getCorreo());
            _entidad.setContrasena(entidad.getContrasena());
            _entidad.setUsuario(entidad.getUsuario());
            _entidad.setFecha_creacion(entidad.getFecha_creacion());
            
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Sesion/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
