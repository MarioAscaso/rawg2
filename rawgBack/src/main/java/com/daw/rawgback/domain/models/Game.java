package com.daw.rawgback.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorites")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("backgroundImage")
    @Column(name = "background_image")
    private String backgroundImage;

    @JsonProperty("rating")
    private double rating;

    public Game(){}

    public Game(Long id, String name, String backgroundImage, double rating) {
        this.id = id;
        this.name = name;
        this.backgroundImage = backgroundImage;
        this.rating = rating;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getBackgroundImage() {return backgroundImage;}
    public double getRating() {return rating;}

    public void setName(String name) {this.name = name;}
    public void setBackgroundImage(String backgroundImage) {this.backgroundImage = backgroundImage;}
    public void setRating(double rating) {this.rating = rating;}
}
