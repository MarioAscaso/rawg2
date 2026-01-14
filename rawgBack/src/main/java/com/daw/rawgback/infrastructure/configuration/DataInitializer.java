package com.daw.rawgback.infrastructure.configuration;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    // CommandLineRunner se ejecuta automáticamente al iniciar la aplicación.
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Comprobamos si existe el usuario admin. Si no, lo creamos.
            // Esto asegura que siempre podamos entrar al sistema recién instalado.
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@rawg.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // IMPORTANTE: Encriptar pass
                admin.setRole("ADMIN");

                userRepository.save(admin);
                System.out.println("✅ Usuario ADMIN creado: admin / admin123");
            }
        };
    }
}