package com.utp.integradorspringboot.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.integradorspringboot.models.PasswordResetToken;
import com.utp.integradorspringboot.models.Sesion;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.PasswordResetTokenRepository;
import com.utp.integradorspringboot.repositories.SesionRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;

import jakarta.mail.MessagingException;

@Service
@Transactional
public class PasswordResetService {
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Transactional
    public boolean requestPasswordReset(String email) {
        System.out.println("DEBUG: Starting requestPasswordReset for email: " + email);
        try {
            // Normalize email
            String normalizedEmail = email.trim().toLowerCase();
            // Debug: Print all emails in the database
            sesionRepository.findAll().forEach(s -> System.out.println("DB email: [" + s.getCorreo() + "]"));
            // Debug: Print the normalized email being searched
            System.out.println("Normalized email being searched: [" + normalizedEmail + "]");
            Optional<Sesion> sesionOpt = sesionRepository.findByCorreo(normalizedEmail);
            if (sesionOpt.isEmpty()) {
                System.out.println("DEBUG: No session found for email: " + normalizedEmail);
                return false; // Email not found
            }
            
            Sesion sesion = sesionOpt.get();
            Long userId = sesion.getUsuario().getId();
            System.out.println("DEBUG: Found user with ID: " + userId);
            
            // Delete any existing tokens for this user
            System.out.println("DEBUG: Deleting existing tokens for user: " + userId);
            List<PasswordResetToken> existingTokens = tokenRepository.findByUserId(userId);
            tokenRepository.deleteAll(existingTokens);
            
            // Generate new token
            String token = UUID.randomUUID().toString();
            LocalDateTime expirationDate = LocalDateTime.now().plusHours(24); // 24 hours expiration
            System.out.println("DEBUG: Generated token: " + token);
            
            PasswordResetToken resetToken = new PasswordResetToken(userId, token, expirationDate);
            tokenRepository.save(resetToken);
            System.out.println("DEBUG: Saved reset token to database");
            
            // Send email
            try {
                String resetLink = "http://localhost:8081/reset-password?token=" + token;
                System.out.println("DEBUG: Sending email with reset link: " + resetLink);
                emailService.sendPasswordResetEmail(normalizedEmail, resetLink, null);
                System.out.println("DEBUG: Email sent successfully");
            } catch (MessagingException emailException) {
                // Log email error but don't fail the entire request
                System.err.println("Failed to send password reset email: " + emailException.getMessage());
                System.out.println("DEBUG: Email sending failed, but continuing with process");
                // Continue with the process even if email fails
            }
            
            System.out.println("DEBUG: Password reset request completed successfully");
            return true;
        } catch (Exception e) {
            System.err.println("ERROR in requestPasswordReset: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        return !resetToken.getUsed() && !resetToken.isExpired();
    }
    
    @Transactional
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
    
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
} 