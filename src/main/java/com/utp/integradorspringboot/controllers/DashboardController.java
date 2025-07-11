package com.utp.integradorspringboot.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Veterinario;
import com.utp.integradorspringboot.models.VeterinarioDTO;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
import com.utp.integradorspringboot.repositories.SesionRepository;
import com.utp.integradorspringboot.repositories.VeterinarioRepository;
import com.utp.integradorspringboot.services.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private GestionCitaRepository citaRepository;
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    // Dashboard de Dueño
    @GetMapping("/dueno/dashboard")
    public String duenoDashboard(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
            return "redirect:/login";
        }
        
        AuthService.UserType userType = authService.getCurrentUserType(session);
        if (userType != AuthService.UserType.DUENO) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
            return "redirect:/";
        }
        
        return "dueno/dashboard";
    }
    
    // Dashboard de Veterinario
    @GetMapping("/veterinario/dashboard")
    public String veterinarioDashboard(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
            return "redirect:/login";
        }
        
        AuthService.UserType userType = authService.getCurrentUserType(session);
        if (userType != AuthService.UserType.VETERINARIO) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
            return "redirect:/";
        }
        
        return "veterinario/dashboard";
    }
    
    // Dashboard de Recepcionista
    @GetMapping("/recepcionista/dashboard")
    public String recepcionistaDashboard(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (!authService.isLoggedIn(session)) {
                redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
                return "redirect:/login";
            }
            
            AuthService.UserType userType = authService.getCurrentUserType(session);
            if (userType != AuthService.UserType.RECEPCIONISTA) {
                redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
                return "redirect:/";
            }
            
            // Obtener citas de hoy
            LocalDate today = LocalDate.now();
            List<Cita> citasHoy = citaRepository.findByFecha(today);
            System.out.println("Citas de hoy encontradas: " + citasHoy.size());
            
            // Depurar: Imprimir las primeras citas para verificar datos
            for (int i = 0; i < Math.min(citasHoy.size(), 3); i++) {
                Cita c = citasHoy.get(i);
                System.out.println("Cita " + (i+1) + ": " + 
                                 "ID=" + c.getId() + 
                                 ", Fecha=" + c.getFecha() + 
                                 ", Hora=" + c.getHora() + 
                                 ", Estado=" + c.getEstado() +
                                 ", Dueno=" + (c.getDueno() != null ? c.getDueno().getId() : "null") +
                                 ", Veterinario=" + (c.getVeterinario() != null ? c.getVeterinario().getId() : "null") +
                                 ", Mascota=" + (c.getMascota() != null ? c.getMascota().getId() : "null"));
            }
            
            // Obtener todos los veterinarios
            List<Veterinario> veterinarios = veterinarioRepository.findAllWithUsuario();
            System.out.println("Veterinarios encontrados: " + veterinarios.size());

            // Construir lista de DTOs
            List<VeterinarioDTO> veterinariosDTO = new ArrayList<>();
            for (Veterinario v : veterinarios) {
                String email = sesionRepository.findByUsuario_Id(v.getUsuario().getId())
                    .map(s -> s.getCorreo()).orElse(null);
                veterinariosDTO.add(new VeterinarioDTO(
                    v.getUsuario().getNombres(),
                    v.getUsuario().getApellidos(),
                    v.getEspecialidad(),
                    v.getUsuario().getCelular(),
                    email
                ));
            }
            
            // Depurar: Imprimir los primeros veterinarios
            for (int i = 0; i < Math.min(veterinarios.size(), 3); i++) {
                Veterinario v = veterinarios.get(i);
                System.out.println("Veterinario " + (i+1) + ": " + 
                                 v.getUsuario().getNombres() + " " + v.getUsuario().getApellidos() + 
                                 " - " + v.getEspecialidad());
            }
            
            // Agregar datos al modelo
            model.addAttribute("citasHoy", citasHoy);
            model.addAttribute("veterinariosDTO", veterinariosDTO);
            
            return "recepcionista/dashboard";
        } catch (Exception e) {
            System.err.println("Error in recepcionistaDashboard: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    // Dashboard de Administrador
    @GetMapping("/administrador/dashboard")
    public String administradorDashboard(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
            return "redirect:/login";
        }
        
        AuthService.UserType userType = authService.getCurrentUserType(session);
        if (userType != AuthService.UserType.ADMINISTRADOR) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
            return "redirect:/";
        }
        
        return "administrador/dashboard";
    }

    
} 