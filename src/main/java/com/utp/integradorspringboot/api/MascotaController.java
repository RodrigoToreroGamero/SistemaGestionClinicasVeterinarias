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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MascotaController {

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    // Obtener todas las mascotas
    @GetMapping("/Mascota")
    public ResponseEntity<List<Mascota>> getAll() {
        try {
            List<Mascota> lista = new ArrayList<>();
            mascotaRepository.findAll().forEach(lista::add);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
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
            Optional<Usuario> usuario = usuarioRepository.findById(mascota.getUsuario().getId());
            if (usuario.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            mascota.setUsuario(usuario.get());
            Mascota nuevaMascota = mascotaRepository.save(mascota);
            return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

        } catch (Exception e) {
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
