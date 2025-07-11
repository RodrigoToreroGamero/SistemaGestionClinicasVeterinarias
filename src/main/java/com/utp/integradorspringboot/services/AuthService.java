package com.utp.integradorspringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.SesionRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
    
    @Autowired
    private SesionRepository sesionRepository;
    
    public enum UserType {
        DUENO("dueno"),
        VETERINARIO("veterinario"),
        ADMINISTRADOR("administrador"),
        RECEPCIONISTA("recepcionista"),
        UNKNOWN("unknown");
        
        private final String value;
        
        UserType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    public boolean login(String email, String password, HttpSession session) {
        Optional<Sesion> sesionOpt = sesionRepository.findByCorreo(email);
        
        if (sesionOpt.isPresent()) {
            Sesion sesion = sesionOpt.get();
            if (sesion.getContrasena().equals(password)) {
                Usuario usuario = sesion.getUsuario();
                session.setAttribute("usuario", usuario);
                session.setAttribute("sesion", sesion);
                session.setAttribute("userType", getUserType(email));
                return true;
            }
        }
        return false;
    }
    
    public void logout(HttpSession session) {
        session.invalidate();
    }
    
    public Usuario getCurrentUser(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }
    
    public Sesion getCurrentSession(HttpSession session) {
        return (Sesion) session.getAttribute("sesion");
    }
    
    public UserType getCurrentUserType(HttpSession session) {
        return (UserType) session.getAttribute("userType");
    }
    
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }
    
    public UserType getUserType(String email) {
        if (email == null) return UserType.UNKNOWN;
        
        String domain = email.toLowerCase();
        if (domain.endsWith("@dueno.com")) {
            return UserType.DUENO;
        } else if (domain.endsWith("@veterinario.com")) {
            return UserType.VETERINARIO;
        } else if (domain.endsWith("@administrador.com")) {
            return UserType.ADMINISTRADOR;
        } else if (domain.endsWith("@recepcionista.com") || domain.endsWith("@gmail.com")) {
            return UserType.RECEPCIONISTA;
        }
        
        return UserType.UNKNOWN;
    }
} 