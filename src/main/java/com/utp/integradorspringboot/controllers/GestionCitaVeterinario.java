package com.utp.integradorspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
import com.utp.integradorspringboot.repositories.VeterinarioRepository;
import com.utp.integradorspringboot.services.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GestionCitaVeterinario {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private GestionCitaRepository gestionCitaRepository;
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;
    
    @GetMapping("/veterinario/citas")
    public String citasVeterinario(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("=== DEBUG: Accediendo a /veterinario/citas ===");
        
        if (!authService.isLoggedIn(session)) {
            System.out.println("DEBUG: Usuario no está logueado");
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
            return "redirect:/login";
        }
        
        AuthService.UserType userType = authService.getCurrentUserType(session);
        System.out.println("DEBUG: Tipo de usuario: " + userType);
        
        if (userType != AuthService.UserType.VETERINARIO) {
            System.out.println("DEBUG: Usuario no es veterinario, es: " + userType);
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
            return "redirect:/";
        }
        
        // Obtener el usuario autenticado
        com.utp.integradorspringboot.models.Usuario usuario = authService.getCurrentUser(session);
        System.out.println("DEBUG: Usuario encontrado: " + (usuario != null ? usuario.getNombres() + " " + usuario.getApellidos() + " (ID: " + usuario.getId() + ")" : "null"));
        
        if (usuario == null) {
            System.out.println("DEBUG: Usuario es null");
            redirectAttributes.addFlashAttribute("error", "No se pudo identificar al usuario actual");
            return "redirect:/veterinario/dashboard";
        }
        
        // Buscar el veterinario correspondiente a este usuario
        com.utp.integradorspringboot.models.Veterinario veterinario = veterinarioRepository.findByUsuario(usuario);
        System.out.println("DEBUG: Veterinario encontrado: " + (veterinario != null ? veterinario.getId() : "null"));
        
        if (veterinario == null) {
            System.out.println("DEBUG: Veterinario es null - creando veterinario para usuario ID: " + usuario.getId());
            // Crear un veterinario para este usuario
            veterinario = new com.utp.integradorspringboot.models.Veterinario();
            veterinario.setUsuario(usuario);
            veterinario.setNumero_colegio_medico("VET" + usuario.getId());
            veterinario.setEspecialidad("Medicina General");
            veterinario = veterinarioRepository.save(veterinario);
            System.out.println("DEBUG: Veterinario creado con ID: " + veterinario.getId());
        }
        
        Long veterinarioId = veterinario.getId();
        List<Cita> citas = gestionCitaRepository.findByVeterinarioId(veterinarioId);
        System.out.println("DEBUG: Citas encontradas: " + citas.size());
        
        model.addAttribute("citas", citas);
        System.out.println("DEBUG: Retornando template veterinario/citas");
        return "veterinario/citas";
    }
} 