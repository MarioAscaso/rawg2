package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController // Indica que esta clase maneja peticiones HTTP y devuelve JSON
@RequestMapping("/user") // Ruta base: http://localhost:8084/user
@CrossOrigin(origins = "*") // Permite peticiones desde el front
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Inyección de dependencias por constructor
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // REGISTRO DE USUARIO
    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Validaciones: comprobar si usuario o email ya existen
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
        }
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya existe");
        }

        // Preparar usuario: encriptar contraseña y asignar rol por defecto
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        // Enviar correo de bienvenida (solo si tiene email)
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
        }

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // LOGIN / OBTENER USUARIO ACTUAL
    // Endpoint "/me" devuelve los datos del usuario que está logueado actualmente.
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        // 'Principal' es inyectado por Spring Security, contiene el nombre del usuario autenticado.
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPassword(null); // SEGURIDAD: Nunca devolvemos la contraseña al front.

        return ResponseEntity.ok(user);
    }

    // LISTAR TODOS LOS USUARIOS (Solo Admin)
    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUsers(Principal principal) {
        // Verificación manual de rol
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<User> users = userRepository.findAll();
        users.forEach(u -> u.setPassword(null)); // Limpiamos passwords

        return ResponseEntity.ok(users);
    }

    // BORRAR USUARIO (Solo Admin)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Principal principal) {
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado");
        }

        return ResponseEntity.notFound().build();
    }

    // Método auxiliar privado para comprobar rol de admin
    private boolean isAdmin(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        return user != null && "ADMIN".equals(user.getRole());
    }
}