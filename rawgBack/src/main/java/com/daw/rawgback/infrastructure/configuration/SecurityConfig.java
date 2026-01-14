package com.daw.rawgback.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Clase de configuración de Spring
@EnableWebSecurity // Habilita la seguridad web
public class SecurityConfig {

    // Define la cadena de filtros de seguridad.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS: Permite peticiones desde el frontend (que corre en otro puerto).
                .cors(Customizer.withDefaults())
                // 2. CSRF: Desactivado porque usamos API REST sin estado (no cookies de sesión complejas).
                .csrf(AbstractHttpConfigurer::disable)
                // 3. Autorización de rutas:
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/user/users").permitAll() // Registro PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/games").permitAll()   // Buscar juegos PÚBLICO
                        .anyRequest().authenticated() // Cualquier otra cosa requiere LOGIN
                )
                // 4. Basic Auth: Usamos cabecera Authorization en cada petición.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Configuración detallada de CORS para permitir al navegador hablar con el servidor.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Permitir cualquier origen (útil desarrollo)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Verbos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Cabeceras permitidas

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a toda la app
        return source;
    }

    // Define el encriptador de contraseñas. BCrypt es el estándar seguro hoy día.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}