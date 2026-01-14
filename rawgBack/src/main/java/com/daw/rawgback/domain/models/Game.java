package com.daw.rawgback.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @JsonProperty: Sirve para mapear el JSON que viene de la API de RAWG.
    // Si en el JSON viene "name", mételo en este campo 'name'.
    @JsonProperty("name")
    @Column(unique = true) // Evitamos duplicar el mismo juego muchas veces.
    private String name;

    @JsonProperty("background_image")
    @Column(name = "background_image")
    private String background_image;

    @JsonProperty("rating")
    private double rating;

    // --- CONSTRUCTORES ---

    public Game() {}

    public Game(String name, String background_image, double rating) {
        this.name = name;
        this.background_image = background_image;
        this.rating = rating;
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBackgroundImage() { return background_image; }
    public void setBackgroundImage(String background_image) { this.background_image = background_image; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}