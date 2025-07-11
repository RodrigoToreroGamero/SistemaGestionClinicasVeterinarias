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

import com.utp.integradorspringboot.models.Dueno;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.DuenoRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DuenoController {

    @Autowired
    DuenoRepository duenoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    // Endpoint de prueba para verificar si el controlador está funcionando
    @GetMapping("/Dueno/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Dueno controller is working!", HttpStatus.OK);
    }

    // Obtener todos los dueños
    @GetMapping("/Dueno")
    public ResponseEntity<List<Dueno>> getAll() {
        try {
            System.out.println("Attempting to fetch all duenos...");
            List<Dueno> lista = new ArrayList<>();
            
            Iterable<Dueno> duenos = duenoRepository.findAll();
            duenos.forEach(lista::add);
            
            System.out.println("Found " + lista.size() + " duenos");

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("Error in getAll duenos: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener dueño por ID
    @GetMapping("/Dueno/{id}")
    public ResponseEntity<Dueno> getById(@PathVariable("id") Long id) {
        try {
            Optional<Dueno> duenoData = duenoRepository.findById(id);

            if (duenoData.isPresent()) {
                return new ResponseEntity<>(duenoData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error getting dueno by id: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener dueño por ID de usuario
    @GetMapping("/Dueno/usuario/{usuarioId}")
    public ResponseEntity<Dueno> getByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            // Primero verificar si el usuario existe
            Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
            if (usuario.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Encontrar dueño por usuario
            List<Dueno> duenos = duenoRepository.findAll();
            Optional<Dueno> dueno = duenos.stream()
                    .filter(d -> d.getUsuario().getId().equals(usuarioId))
                    .findFirst();

            if (dueno.isPresent()) {
                return new ResponseEntity<>(dueno.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error getting dueno by usuario id: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear nuevo dueño
    @PostMapping("/Dueno")
    public ResponseEntity<Dueno> create(@RequestBody Dueno dueno) {
        try {
            // Validar que el usuario exista
            if (dueno.getUsuario() == null || dueno.getUsuario().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Optional<Usuario> usuario = usuarioRepository.findById(dueno.getUsuario().getId());
            if (usuario.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            dueno.setUsuario(usuario.get());
            
            Dueno nuevoDueno = duenoRepository.save(dueno);
            return new ResponseEntity<>(nuevoDueno, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Error creating dueno: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar dueño
    @PutMapping("/Dueno/{id}")
    public ResponseEntity<Dueno> update(@PathVariable("id") Long id, @RequestBody Dueno dueno) {
        try {
            Optional<Dueno> duenoData = duenoRepository.findById(id);

            if (duenoData.isPresent()) {
                Dueno _dueno = duenoData.get();
                
                // Actualizar usuario si se proporciona
                if (dueno.getUsuario() != null && dueno.getUsuario().getId() != null) {
                    Optional<Usuario> usuario = usuarioRepository.findById(dueno.getUsuario().getId());
                    if (usuario.isPresent()) {
                        _dueno.setUsuario(usuario.get());
                    }
                }

                return new ResponseEntity<>(duenoRepository.save(_dueno), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error updating dueno: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar dueño
    @DeleteMapping("/Dueno/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            Optional<Dueno> dueno = duenoRepository.findById(id);
            if (dueno.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            duenoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error deleting dueno: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 