/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.utp.integradorspringboot.models.Email;

import jakarta.mail.internet.MimeMessage;

/**
 *
 * @author jcerv_pm92n0w
 */
@Service
public class EmailService {
    public static void SolicitarEnvio(Email email, JavaMailSender emailSender) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jcervanteslivon@gmail.com");
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getMessage());
            emailSender.send(message);
        } catch (Exception e) {
            // Log error
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent, JavaMailSender emailSender) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("jcervanteslivon@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (Exception e) {
            // Log error
        }
    }

    public void sendPasswordResetEmail(String to, String resetLink, JavaMailSender emailSender) {
        String subject = "Restablecimiento de contraseña";
        String htmlContent = """
            <html>
            <body>
                <h2>Solicitud de restablecimiento de contraseña</h2>
                <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.</p>
                <p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>
                <p><a href='" + resetLink + "'>Restablecer contraseña</a></p>
                <p>Si no solicitaste este cambio, puedes ignorar este correo.</p>
            </body>
            </html>
        """;
        sendHtmlEmail(to, subject, htmlContent, emailSender);
    }
}
