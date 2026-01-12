package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Encriptamos la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Por defecto todos son USER, tú puedes cambiarlo
        if (user.getRole() == null) user.setRole("ROLE_USER");

        userRepository.save(user);
        return "Usuario registrado con éxito";
    }

    @GetMapping("/login")
    public String login() {
        return "Autenticado correctamente";
    }
}