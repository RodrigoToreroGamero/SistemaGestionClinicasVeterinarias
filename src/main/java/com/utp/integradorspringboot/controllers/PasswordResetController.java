package com.utp.integradorspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.integradorspringboot.services.PasswordResetService;

@Controller
public class PasswordResetController {
    
    @Autowired
    private PasswordResetService passwordResetService;
    
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, 
                                      RedirectAttributes redirectAttributes) {
        boolean success = passwordResetService.requestPasswordReset(email);
        
        if (success) {
            redirectAttributes.addFlashAttribute("message", 
                "Se ha enviado un enlace de restablecimiento a tu correo electrónico.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", 
                "No se encontró una cuenta con ese correo electrónico.");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/forgot-password";
    }
    
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        boolean validToken = passwordResetService.validateToken(token);
        
        if (!validToken) {
            model.addAttribute("error", "El enlace de restablecimiento no es válido o ha expirado.");
            return "auth/reset-password";
        }
        
        model.addAttribute("token", token);
        return "auth/reset-password";
    }
    
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                     @RequestParam String password,
                                     @RequestParam String confirmPassword,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        
        // Validate token
        if (!passwordResetService.validateToken(token)) {
            model.addAttribute("error", "El enlace de restablecimiento no es válido o ha expirado.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        // Validate password length
        if (password.length() < 6) {
            model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        // Reset password
        boolean success = passwordResetService.resetPassword(token, password);
        
        if (success) {
            redirectAttributes.addFlashAttribute("message", 
                "Tu contraseña ha sido restablecida exitosamente. Puedes iniciar sesión ahora.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Error al restablecer la contraseña. Inténtalo de nuevo.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
    }
} 