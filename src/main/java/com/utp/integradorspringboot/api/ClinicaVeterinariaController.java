package com.utp.integradorspringboot.api;
/*
import com.utp.integradorspringboot.models.ClinicaVeterinaria;
import com.utp.integradorspringboot.models.Veterinario;
import com.utp.integradorspringboot.repositories.ClinicaVeterinariaRepository;
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
    public ResponseEntity<List<ClinicaVeterinaria>> getAll(@RequestParam(required = false) String title) {
        try {
            List<ClinicaVeterinaria> lista = new ArrayList<ClinicaVeterinaria>();
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
        public List<ClinicaVeterinaria> testClinicasVeterinarias() {
        return repository.findAll();
    }

    @GetMapping("/clinicaVeterinaria/{id}")
    public ResponseEntity<ClinicaVeterinaria> getById(@PathVariable("id") Long id) {
        Optional<ClinicaVeterinaria> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clinicaVeterinaria")    
    public ResponseEntity<ClinicaVeterinaria> create(@RequestBody ClinicaVeterinaria entidad) {
        try {
                        
            ClinicaVeterinaria _entidad = repository.save(new ClinicaVeterinaria(
                    null,
                    entidad.getNombreClinica(),
                    entidad.getRuc(),
                    entidad.getDireccionSede(),
                    entidad.getLinkWeb(),
                    entidad.getTelefonoSede(),
                    null
            ));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
    @PutMapping("/clinicaVeterinaria/{id}")
    public ResponseEntity<ClinicaVeterinaria> update(@PathVariable("id") Long id, @RequestBody ClinicaVeterinaria entidad) {
        ClinicaVeterinaria _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setNombreClinica(entidad.getNombreClinica());
            _entidad.setRuc(entidad.getRuc());
            _entidad.setDireccionSede(entidad.getDireccionSede());
            _entidad.setLinkWeb(entidad.getLinkWeb());
            _entidad.setTelefonoSede(entidad.getTelefonoSede());
            _entidad.setMedicoResponsable(entidad.getMedicoResponsable());
            
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
  */
