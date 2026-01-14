package com.daw.rawgback.infrastructure.security;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.infrastructure.repositories.JpaUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Esta clase es el "puente" entre Spring Security y nuestra base de datos.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaUserRepository userRepository;

    public CustomUserDetailsService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Este metodo lo llama Spring automáticamente cuando alguien intenta hacer login.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en nuestra tabla 'users'.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Convertimos nuestro objeto 'User' a un objeto 'UserDetails' que entiende Spring Security.
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Spring comparará este hash con el que viene del login.
                .roles(user.getRole() != null ? user.getRole() : "USER") // Asignamos roles.
                .build();
    }
}