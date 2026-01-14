package com.daw.rawgback.domain.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true) // No puede haber dos usuarios con el mismo correo
    private String email;

    private String password;
    private String role;

    public User(){}

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> favorites = new ArrayList<>();

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getRole() {return role;}
    public List<Game> getFavorites() {return favorites;}
    public String getEmail() {return email;}

    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) {this.role = role;}
    public void setFavorites(List<Game> favorites) {this.favorites = favorites;}
    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role + "'}";
    }
}