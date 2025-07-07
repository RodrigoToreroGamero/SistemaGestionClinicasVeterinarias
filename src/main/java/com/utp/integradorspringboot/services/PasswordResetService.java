package com.utp.integradorspringboot.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.utp.integradorspringboot.models.PasswordResetToken;
import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.PasswordResetTokenRepository;
import com.utp.integradorspringboot.repositories.SesionRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;

@Service
public class PasswordResetService {
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    public boolean requestPasswordReset(String email) {
        try {
            // Find user by email
            Optional<Sesion> sesionOpt = sesionRepository.findByCorreo(email);
            if (sesionOpt.isEmpty()) {
                return false; // Email not found
            }
            
            Sesion sesion = sesionOpt.get();
            Long userId = sesion.getUsuario().getId();
            
            // Delete any existing tokens for this user
            List<PasswordResetToken> existingTokens = tokenRepository.findByUserId(userId);
            tokenRepository.deleteAll(existingTokens);
            
            // Generate new token
            String token = UUID.randomUUID().toString();
            LocalDateTime expirationDate = LocalDateTime.now().plusHours(24); // 24 hours expiration
            
            PasswordResetToken resetToken = new PasswordResetToken(userId, token, expirationDate);
            tokenRepository.save(resetToken);
            
            // Send email
            String resetLink = "http://localhost:8081/reset-password?token=" + token;
            emailService.sendPasswordResetEmail(email, resetLink, mailSender);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        return !resetToken.getUsed() && !resetToken.isExpired();
    }
    
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        if (resetToken.getUsed() || resetToken.isExpired()) {
            return false;
        }
        
        try {
            // Find the user
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(resetToken.getUserId());
            if (usuarioOpt.isEmpty()) {
                return false;
            }
            
            // Find the session by user ID (we need to add this method to repository)
            // For now, we'll use a different approach - find by user
            List<Sesion> sesiones = sesionRepository.findAll();
            Optional<Sesion> sesionOpt = sesiones.stream()
                .filter(s -> s.getUsuario().getId().equals(resetToken.getUserId()))
                .findFirst();
            
            if (sesionOpt.isEmpty()) {
                return false;
            }
            
            Sesion sesion = sesionOpt.get();
            sesion.setContrasena(newPassword);
            sesionRepository.save(sesion);
            
            // Mark token as used
            resetToken.setUsed(true);
            tokenRepository.save(resetToken);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
} 