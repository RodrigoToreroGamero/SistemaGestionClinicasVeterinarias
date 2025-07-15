package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.PersonalDTO;
import com.utp.integradorspringboot.models.Clinica;
import com.utp.integradorspringboot.models.Recepcionista;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.models.Veterinario;
import com.utp.integradorspringboot.repositories.ClinicaRepository;
import com.utp.integradorspringboot.repositories.RecepcionistaRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.repositories.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de personal
 * Combina datos de Veterinario y Recepcionista
 */
@Service
public class GestionPersonalService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    /**
     * Obtiene todo el personal combinando Veterinarios y Recepcionistas
     */
    public List<PersonalDTO> getAllPersonal() {
        List<PersonalDTO> personalList = new ArrayList<>();

        // Obtener veterinarios
        List<Veterinario> veterinarios = veterinarioRepository.findAll();
        for (Veterinario veterinario : veterinarios) {
            Usuario usuario = veterinario.getUsuario();
            PersonalDTO dto = new PersonalDTO();
            dto.setId(veterinario.getId());
            dto.setTipo("Veterinario");
            dto.setDni(usuario.getDni());
            dto.setNombres(usuario.getNombres());
            dto.setApellidos(usuario.getApellidos());
            dto.setCelular(usuario.getCelular());
            dto.setFechaNacimiento(usuario.getFecha_nacimiento());
            dto.setFechaRegistro(usuario.getFecha_registro());
            dto.setNumeroColegioMedico(veterinario.getNumero_colegio_medico());
            dto.setEspecialidad(veterinario.getEspecialidad());
            personalList.add(dto);
        }

        // Obtener recepcionistas
        List<Recepcionista> recepcionistas = recepcionistaRepository.findAll();
        for (Recepcionista recepcionista : recepcionistas) {
            Usuario usuario = recepcionista.getUsuario();
            Clinica clinica = recepcionista.getClinica();
            PersonalDTO dto = new PersonalDTO();
            dto.setId(recepcionista.getId());
            dto.setTipo("Recepcionista");
            dto.setDni(usuario.getDni());
            dto.setNombres(usuario.getNombres());
            dto.setApellidos(usuario.getApellidos());
            dto.setCelular(usuario.getCelular());
            dto.setFechaNacimiento(usuario.getFecha_nacimiento());
            dto.setFechaRegistro(usuario.getFecha_registro());
            dto.setClinica(clinica != null ? clinica.getNombre_clinica() : "");
            dto.setClinicaId(clinica != null ? clinica.getId() : null);
            personalList.add(dto);
        }

        return personalList;
    }

    /**
     * Obtiene personal por ID y tipo
     */
    public PersonalDTO getPersonalById(Long id, String tipo) {
        if ("Veterinario".equals(tipo)) {
            Optional<Veterinario> veterinarioOpt = veterinarioRepository.findById(id);
            if (veterinarioOpt.isPresent()) {
                Veterinario veterinario = veterinarioOpt.get();
                Usuario usuario = veterinario.getUsuario();
                PersonalDTO dto = new PersonalDTO();
                dto.setId(veterinario.getId());
                dto.setTipo("Veterinario");
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                dto.setCelular(usuario.getCelular());
                dto.setFechaNacimiento(usuario.getFecha_nacimiento());
                dto.setFechaRegistro(usuario.getFecha_registro());
                dto.setNumeroColegioMedico(veterinario.getNumero_colegio_medico());
                dto.setEspecialidad(veterinario.getEspecialidad());
                return dto;
            }
        } else if ("Recepcionista".equals(tipo)) {
            Optional<Recepcionista> recepcionistaOpt = recepcionistaRepository.findById(id);
            if (recepcionistaOpt.isPresent()) {
                Recepcionista recepcionista = recepcionistaOpt.get();
                Usuario usuario = recepcionista.getUsuario();
                Clinica clinica = recepcionista.getClinica();
                PersonalDTO dto = new PersonalDTO();
                dto.setId(recepcionista.getId());
                dto.setTipo("Recepcionista");
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                dto.setCelular(usuario.getCelular());
                dto.setFechaNacimiento(usuario.getFecha_nacimiento());
                dto.setFechaRegistro(usuario.getFecha_registro());
                dto.setClinica(clinica != null ? clinica.getNombre_clinica() : "");
                dto.setClinicaId(clinica != null ? clinica.getId() : null);
                return dto;
            }
        }
        return null;
    }

    /**
     * Crea nuevo personal
     */
    public PersonalDTO createPersonal(PersonalDTO personalDTO) {
        // Crear usuario primero
        Usuario usuario = new Usuario();
        usuario.setNombres(personalDTO.getNombres());
        usuario.setApellidos(personalDTO.getApellidos());
        usuario.setDni(personalDTO.getDni());
        usuario.setCelular(personalDTO.getCelular());
        usuario.setFecha_nacimiento(personalDTO.getFechaNacimiento());
        usuario.setFecha_registro(LocalDateTime.now());
        
        Usuario savedUsuario = usuarioRepository.save(usuario);

        if ("Veterinario".equals(personalDTO.getTipo())) {
            Veterinario veterinario = new Veterinario();
            veterinario.setUsuario(savedUsuario);
            veterinario.setNumero_colegio_medico(personalDTO.getNumeroColegioMedico());
            veterinario.setEspecialidad(personalDTO.getEspecialidad());
            
            Veterinario savedVeterinario = veterinarioRepository.save(veterinario);
            personalDTO.setId(savedVeterinario.getId());
            personalDTO.setFechaRegistro(savedUsuario.getFecha_registro());
            
        } else if ("Recepcionista".equals(personalDTO.getTipo())) {
            Recepcionista recepcionista = new Recepcionista();
            recepcionista.setUsuario(savedUsuario);
            
            if (personalDTO.getClinicaId() != null) {
                Optional<Clinica> clinicaOpt = clinicaRepository.findById(personalDTO.getClinicaId());
                clinicaOpt.ifPresent(recepcionista::setClinica);
            }
            
            Recepcionista savedRecepcionista = recepcionistaRepository.save(recepcionista);
            personalDTO.setId(savedRecepcionista.getId());
            personalDTO.setFechaRegistro(savedUsuario.getFecha_registro());
        }

        return personalDTO;
    }

    /**
     * Actualiza personal existente
     */
    public PersonalDTO updatePersonal(Long id, PersonalDTO personalDTO) {
        if ("Veterinario".equals(personalDTO.getTipo())) {
            Optional<Veterinario> veterinarioOpt = veterinarioRepository.findById(id);
            if (veterinarioOpt.isPresent()) {
                Veterinario veterinario = veterinarioOpt.get();
                Usuario usuario = veterinario.getUsuario();
                
                // Actualizar datos del usuario
                usuario.setNombres(personalDTO.getNombres());
                usuario.setApellidos(personalDTO.getApellidos());
                usuario.setDni(personalDTO.getDni());
                usuario.setCelular(personalDTO.getCelular());
                usuario.setFecha_nacimiento(personalDTO.getFechaNacimiento());
                
                // Actualizar datos del veterinario
                veterinario.setNumero_colegio_medico(personalDTO.getNumeroColegioMedico());
                veterinario.setEspecialidad(personalDTO.getEspecialidad());
                
                usuarioRepository.save(usuario);
                veterinarioRepository.save(veterinario);
                
                personalDTO.setId(veterinario.getId());
                personalDTO.setFechaRegistro(usuario.getFecha_registro());
                return personalDTO;
            }
        } else if ("Recepcionista".equals(personalDTO.getTipo())) {
            Optional<Recepcionista> recepcionistaOpt = recepcionistaRepository.findById(id);
            if (recepcionistaOpt.isPresent()) {
                Recepcionista recepcionista = recepcionistaOpt.get();
                Usuario usuario = recepcionista.getUsuario();
                
                // Actualizar datos del usuario
                usuario.setNombres(personalDTO.getNombres());
                usuario.setApellidos(personalDTO.getApellidos());
                usuario.setDni(personalDTO.getDni());
                usuario.setCelular(personalDTO.getCelular());
                usuario.setFecha_nacimiento(personalDTO.getFechaNacimiento());
                
                // Actualizar clínica si se proporciona
                if (personalDTO.getClinicaId() != null) {
                    Optional<Clinica> clinicaOpt = clinicaRepository.findById(personalDTO.getClinicaId());
                    clinicaOpt.ifPresent(recepcionista::setClinica);
                }
                
                usuarioRepository.save(usuario);
                recepcionistaRepository.save(recepcionista);
                
                personalDTO.setId(recepcionista.getId());
                personalDTO.setFechaRegistro(usuario.getFecha_registro());
                return personalDTO;
            }
        }
        return null;
    }

    /**
     * Elimina personal por ID y tipo
     */
    public boolean deletePersonal(Long id, String tipo) {
        try {
            if ("Veterinario".equals(tipo)) {
                Optional<Veterinario> veterinarioOpt = veterinarioRepository.findById(id);
                if (veterinarioOpt.isPresent()) {
                    Veterinario veterinario = veterinarioOpt.get();
                    Usuario usuario = veterinario.getUsuario();
                    veterinarioRepository.delete(veterinario);
                    usuarioRepository.delete(usuario);
                    return true;
                }
            } else if ("Recepcionista".equals(tipo)) {
                Optional<Recepcionista> recepcionistaOpt = recepcionistaRepository.findById(id);
                if (recepcionistaOpt.isPresent()) {
                    Recepcionista recepcionista = recepcionistaOpt.get();
                    Usuario usuario = recepcionista.getUsuario();
                    recepcionistaRepository.delete(recepcionista);
                    usuarioRepository.delete(usuario);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
} 