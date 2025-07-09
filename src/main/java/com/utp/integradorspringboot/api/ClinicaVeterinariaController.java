package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Clinica;
import com.utp.integradorspringboot.repositories.ClinicaVeterinariaRepository;
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
public class ClinicaVeterinariaController {

    @Autowired
    ClinicaVeterinariaRepository repository;

    @GetMapping("/clinicaVeterinaria")
    public ResponseEntity<List<Clinica>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Clinica> lista = new ArrayList<Clinica>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @GetMapping("/clinicaVeterinaria/test")
        public List<Clinica> testClinicasVeterinarias() {
        return repository.findAll();
    }

    @GetMapping("/clinicaVeterinaria/{id}")
    public ResponseEntity<Clinica> getById(@PathVariable("id") Long id) {
        Optional<Clinica> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clinicaVeterinaria")    
    public ResponseEntity<Clinica> create(@RequestBody Clinica entidad) {
        try {
                        
            Clinica _entidad = repository.save(new Clinica(
                    null,
                    entidad.getNombre_clinica(),
                    entidad.getRuc(),
                    entidad.getDireccion(),
                    entidad.getTelefono(),
                    entidad.getLink_web(),
                    //entidad.getVeterinario(),
                    entidad.getPlan_suscripcion(),
                    entidad.getPasarela_pago()
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
    
    @PutMapping("/clinicaVeterinaria/{id}")
    public ResponseEntity<Clinica> update(@PathVariable("id") Long id, @RequestBody Clinica entidad) {
        Clinica _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
        
            _entidad.setNombre_clinica(entidad.getNombre_clinica());
            _entidad.setRuc(entidad.getRuc());
            _entidad.setDireccion(entidad.getDireccion());
            _entidad.setTelefono(entidad.getTelefono());
            _entidad.setLink_web(entidad.getLink_web());
            //_entidad.setVeterinario(entidad.getVeterinario());
            _entidad.setPlan_suscripcion(entidad.getPlan_suscripcion());
            _entidad.setPasarela_pago(entidad.getPasarela_pago());
            
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/clinicaVeterinaria/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());            
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

