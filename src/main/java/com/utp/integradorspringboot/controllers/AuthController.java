package com.utp.integradorspringboot.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.SesionRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.services.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password, 
                       HttpSession session, 
                       RedirectAttributes redirectAttributes) {
        
        if (authService.login(email, password, session)) {
            Usuario usuario = authService.getCurrentUser(session);
            AuthService.UserType userType = authService.getCurrentUserType(session);
            
            // Redirect based on user type
            switch (userType) {
                case DUENO:
                    return "redirect:/dueno/dashboard";
                case VETERINARIO:
                    return "redirect:/veterinario/dashboard";
                case ADMINISTRADOR:
                    return "redirect:/administrador/dashboard";
                case RECEPCIONISTA:
                    return "redirect:/recepcionista/dashboard";
                default:
                    return "redirect:/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String nombres,
                          @RequestParam String apellidos,
                          @RequestParam String dni,
                          @RequestParam String celular,
                          @RequestParam String email,
                          @RequestParam String password,
                          RedirectAttributes redirectAttributes) {
        
        // Check if email already exists
        if (sesionRepository.findByCorreo(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/register";
        }
        
        // Create new user
        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setDni(dni);
        usuario.setCelular(celular);
        usuario.setFecha_registro(LocalDateTime.now());
        
        usuario = usuarioRepository.save(usuario);
        
        // Create session for the user
        Sesion sesion = new Sesion();
        sesion.setCorreo(email);
        sesion.setContrasena(password);
        sesion.setUsuario(usuario);
        sesion.setFecha_creacion(LocalDateTime.now());
        
        sesionRepository.save(sesion);
        
        redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente");
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/";
    }
} 