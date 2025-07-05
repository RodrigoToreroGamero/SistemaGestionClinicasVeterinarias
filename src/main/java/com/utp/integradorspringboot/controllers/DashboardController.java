package com.utp.integradorspringboot.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.models.Cita;
import com.utp.integradorspringboot.models.Veterinario;
import com.utp.integradorspringboot.repositories.GestionCitaRepository;
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
    
    // Dueño Dashboard
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
    
    // Veterinario Dashboard
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
    
    // Recepcionista Dashboard
    @GetMapping("/recepcionista/dashboard")
    public String recepcionistaDashboard(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder a esta página");
            return "redirect:/login";
        }
        
        AuthService.UserType userType = authService.getCurrentUserType(session);
        if (userType != AuthService.UserType.RECEPCIONISTA) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta página");
            return "redirect:/";
        }
        
        // Get today's appointments
        LocalDate today = LocalDate.now();
        List<Cita> citasHoy = citaRepository.findByFecha(today);
        System.out.println("Citas de hoy encontradas: " + citasHoy.size());
        
        // Get all veterinarians
        List<Veterinario> veterinarios = veterinarioRepository.findAll();
        System.out.println("Veterinarios encontrados: " + veterinarios.size());
        
        // Debug: Print first few veterinarians
        for (int i = 0; i < Math.min(veterinarios.size(), 3); i++) {
            Veterinario v = veterinarios.get(i);
            System.out.println("Veterinario " + (i+1) + ": " + 
                             v.getUsuario().getNombres() + " " + v.getUsuario().getApellidos() + 
                             " - " + v.getEspecialidad());
        }
        
        // Add data to model
        model.addAttribute("citasHoy", citasHoy);
        model.addAttribute("veterinarios", veterinarios);
        
        return "recepcionista/dashboard";
    }
    
    // Administrador Dashboard
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