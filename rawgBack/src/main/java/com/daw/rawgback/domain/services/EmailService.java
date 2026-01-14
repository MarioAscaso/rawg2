package com.daw.rawgback.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // Marca la clase como un Servicio de Spring
public class EmailService {

    // Spring Boot Starter Mail nos configura esto automáticamente leyendo application.properties
    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            // Creamos un mensaje simple
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ascasovicentemario@gmail.com"); // Remitente
            message.setTo(toEmail); // Destinatario
            message.setSubject("¡Bienvenido a RAWG App! 🎮");
            message.setText("Hola " + username + ",\n\n" +
                            "¡Gracias por registrarte en RAWG App!\n" +
                            "Ya puedes empezar a buscar tus juegos favoritos y crear tu colección.\n");

            // Enviamos el correo
            mailSender.send(message);
            System.out.println("📧 Correo enviado a " + toEmail);

        } catch (Exception e) {
            // Capturamos la excepción para que, si falla el correo, NO falle el registro del usuario.
            System.err.println("❌ Error enviando correo: " + e.getMessage());
        }
    }
}