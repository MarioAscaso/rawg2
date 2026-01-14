package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.domain.models.User;
import com.daw.rawgback.domain.repositories.UserRepository;
import com.daw.rawgback.domain.services.EmailService; // Importante: Tu servicio de email
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*") // Permite peticiones desde el Frontend
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // Servicio para enviar correos

    // Inyección de dependencias por constructor
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // ==========================================
    // 1. REGISTRO DE USUARIOS (Con Email)
    // ==========================================
    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody User user) {
        // 1. Validar si el usuario o el email ya existen
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
        }
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está registrado");
        }

        // 2. Preparar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Por defecto todos son usuarios normales

        // 3. Guardar en Base de Datos
        userRepository.save(user);

        // 4. Enviar correo de bienvenida (Si tiene email)
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            // Esto puede tardar unos segundos, en una app real se haría asíncrono,
            // pero para empezar está perfecto aquí.
            emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
        }

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // ==========================================
    // 2. OBTENER USUARIO ACTUAL (Login/Me)
    // ==========================================
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        // 'principal' contiene el usuario logueado gracias a Spring Security
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // POR SEGURIDAD: Borramos la contraseña antes de enviarla al frontend
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

    // ==========================================
    // 3. ZONA ADMIN: LISTAR TODOS
    // ==========================================
    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUsers(Principal principal) {
        // Verificamos que sea ADMIN
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<User> users = userRepository.findAll();

        // Limpiamos las contraseñas de todos por seguridad
        users.forEach(u -> u.setPassword(null));

        return ResponseEntity.ok(users);
    }

    // ==========================================
    // 4. ZONA ADMIN: ELIMINAR USUARIO
    // ==========================================
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Principal principal) {
        // Verificamos que sea ADMIN
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado");
        }

        return ResponseEntity.notFound().build();
    }

    // ==========================================
    // MÉTODO AUXILIAR PRIVADO
    // ==========================================
    private boolean isAdmin(Principal principal) {
        // Buscamos al usuario que hace la petición
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        // Comprobamos si existe y si su rol es ADMIN
        return user != null && "ADMIN".equals(user.getRole());
    }
}