package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Mascota;
import com.utp.integradorspringboot.repositories.MascotaRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MascotaController {

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    // Test endpoint to check if the controller is working
    @GetMapping("/Mascota/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Mascota controller is working!", HttpStatus.OK);
    }

    // Simple test endpoint without database
    @GetMapping("/Mascota/simple")
    public ResponseEntity<Map<String, Object>> simpleTest() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Simple test successful");
            response.put("timestamp", new Date());
            response.put("status", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in simple test: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Test database connection only
    @GetMapping("/Mascota/db-test")
    public ResponseEntity<Map<String, Object>> dbTest() {
        try {
            long count = mascotaRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Database connection successful");
            response.put("mascota_count", count);
            response.put("status", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in database test: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todas las mascotas
    @GetMapping("/Mascota")
    public ResponseEntity<List<Mascota>> getAll() {
        try {
            System.out.println("Attempting to fetch all mascotas...");
            List<Mascota> lista = new ArrayList<>();
            
            // Try to fetch data with more detailed error handling
            Iterable<Mascota> mascotas = mascotaRepository.findAll();
            mascotas.forEach(lista::add);
            
            System.out.println("Found " + lista.size() + " mascotas");

            for (Mascota m : lista) {
                System.out.println(m);
            }

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (org.springframework.dao.DataAccessException e) {
            System.err.println("Database access error in getAll mascotas: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Unexpected error in getAll mascotas: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener mascota por ID
    @GetMapping("/Mascota/{id}")
    public ResponseEntity<Mascota> getById(@PathVariable("id") Long id) {
        Optional<Mascota> mascotaData = mascotaRepository.findById(id);

        if (mascotaData.isPresent()) {
            return new ResponseEntity<>(mascotaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener mascotas por ID de usuario
    @GetMapping("/Mascota/usuario/{usuarioId}")
    public ResponseEntity<List<Mascota>> getByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<Mascota> mascotas = mascotaRepository.findByUsuarioId(usuarioId);

            if (mascotas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(mascotas, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear nueva mascota
    @PostMapping("/Mascota")
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        try {
            // Validar que el usuario exista
            if (mascota.getUsuario() == null || mascota.getUsuario().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Optional<Usuario> usuario = usuarioRepository.findById(mascota.getUsuario().getId());
            if (usuario.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            mascota.setUsuario(usuario.get());
            
            // Validar campos requeridos
            if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Mascota nuevaMascota = mascotaRepository.save(mascota);
            return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // Para debugging
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar mascota
    @PutMapping("/Mascota/{id}")
    public ResponseEntity<Mascota> update(@PathVariable("id") Long id, @RequestBody Mascota mascota) {
        Optional<Mascota> mascotaData = mascotaRepository.findById(id);

        if (mascotaData.isPresent()) {
            Mascota _mascota = mascotaData.get();
            _mascota.setNombre(mascota.getNombre());
            _mascota.setRaza(mascota.getRaza());
            _mascota.setEdad(mascota.getEdad());

            // Opcional: actualizar usuario si lo necesitas
            if (mascota.getUsuario() != null && mascota.getUsuario().getId() != null) {
                Optional<Usuario> usuario = usuarioRepository.findById(mascota.getUsuario().getId());
                usuario.ifPresent(_mascota::setUsuario);
            }

            return new ResponseEntity<>(mascotaRepository.save(_mascota), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar mascota
    @DeleteMapping("/Mascota/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            mascotaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
