package com.daw.rawgback.domain.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Entity: Dice que esta clase representa una tabla en la base de datos (JPA).
@Entity
@Table(name = "users") // Nombre real de la tabla en MySQL.
public class User {

    @Id // Clave primaria (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT en MySQL.
    private Long id;

    @Column(unique = true) // Restricción: No puede haber dos usuarios con el mismo nombre.
    private String username;

    @Column(unique = true) // Restricción: El email también debe ser único.
    private String email;

    private String password;
    private String role; // Rol para seguridad ("ADMIN" o "USER").

    // Relación ManyToMany: Un usuario tiene muchos juegos, un juego puede estar en muchos usuarios.
    // fetch = EAGER: "Ansioso". Cuando cargo el Usuario de la BD, cárgame TAMBIÉN sus juegos favoritos al instante.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_favorites", // Tabla intermedia que crea JPA automáticamente.
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> favorites = new ArrayList<>();

    // --- CONSTRUCTORES ---

    // Constructor vacío (Obligatorio para JPA/Hibernate).
    public User() {}

    // Constructor con campos (Para crear usuarios nosotros fácilmente).
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // --- GETTERS Y SETTERS (Manuales sin Lombok) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Game> getFavorites() { return favorites; }
    public void setFavorites(List<Game> favorites) { this.favorites = favorites; }

    // toString (Opcional, útil para depurar/logs).
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role + "'}";
    }
}