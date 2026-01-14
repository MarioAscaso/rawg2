package com.daw.rawgback.domain.services; // Ajusta el paquete según tu estructura

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("TU_CORREO@gmail.com"); // Pon el mismo correo que en properties
            message.setTo(toEmail);
            message.setSubject("¡Bienvenido a RAWG App! 🎮");
            message.setText("Hola " + username + ",\n\n" +
                            "¡Gracias por registrarte en RAWG App!\n" +
                            "Ya puedes empezar a buscar tus juegos favoritos y crear tu colección.\n\n" +
                            "Saludos,\n" +
                            "El equipo de RAWG App.");

            mailSender.send(message);
            System.out.println("📧 Correo enviado a " + toEmail);

        } catch (Exception e) {
            System.err.println("❌ Error enviando correo: " + e.getMessage());
            // No lanzamos error para no bloquear el registro si falla el correo
        }
    }
}