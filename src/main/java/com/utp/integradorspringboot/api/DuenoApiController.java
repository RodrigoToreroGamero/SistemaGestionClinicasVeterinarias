package com.utp.integradorspringboot.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.repositories.DuenoRepository;
import com.utp.integradorspringboot.repositories.SesionRepository;

@RestController
@RequestMapping("/api/duenos")
public class DuenoApiController {
    @Autowired
    private DuenoRepository duenoRepository;
    @Autowired
    private SesionRepository sesionRepository;

    public static class DuenoDTO {
        public Long id;
        public String dni;
        public String nombres;
        public String apellidos;
        public String celular;
        public String email;
        public DuenoDTO(Long id, String dni, String nombres, String apellidos, String celular, String email) {
            this.id = id;
            this.dni = dni;
            this.nombres = nombres;
            this.apellidos = apellidos;
            this.celular = celular;
            this.email = email;
        }
    }

    @GetMapping
    @ResponseBody
    public List<DuenoDTO> getAllDuenos() {
        return duenoRepository.findAll().stream().map(dueno -> {
            String email = null;
            if (dueno.getUsuario() != null) {
                // Buscar sesión por ID de usuario
                Sesion sesion = sesionRepository.findByUsuario_Id(dueno.getUsuario().getId()).orElse(null);
                if (sesion != null) {
                    email = sesion.getCorreo();
                }
            }
            return new DuenoDTO(
                dueno.getId(),
                dueno.getUsuario() != null ? dueno.getUsuario().getDni() : null,
                dueno.getUsuario() != null ? dueno.getUsuario().getNombres() : null,
                dueno.getUsuario() != null ? dueno.getUsuario().getApellidos() : null,
                dueno.getUsuario() != null ? dueno.getUsuario().getCelular() : null,
                email
            );
        }).collect(Collectors.toList());
    }
} 