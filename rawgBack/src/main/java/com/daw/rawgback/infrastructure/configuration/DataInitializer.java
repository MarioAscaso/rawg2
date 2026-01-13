package com.daw.rawgback.infrastructure.configuration;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository; // <--- IMPORTANTE: Importa este, no JpaUserRepository
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    // CAMBIO AQUÍ: Usamos 'UserRepository' en lugar de 'JpaUserRepository'
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");

                userRepository.save(admin); // Ahora Java sabrá qué 'save' usar
                System.out.println("✅ Usuario ADMIN creado: admin / admin123");
            }
        };
    }
}