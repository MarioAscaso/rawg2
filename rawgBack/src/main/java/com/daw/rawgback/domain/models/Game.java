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

    @JsonProperty("background_image")
    @Column(name = "background_image")
    private String background_image;

    @JsonProperty("rating")
    private double rating;

    public Game(){}

    public Game(Long id, String name, String background_image, double rating) {
        this.id = id;
        this.name = name;
        this.background_image = background_image;
        this.rating = rating;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getBackgroundImage() {return background_image;}
    public double getRating() {return rating;}

    public void setName(String name) {this.name = name;}
    public void setBackgroundImage(String backgroundImage) {this.background_image = backgroundImage;}
    public void setRating(double rating) {this.rating = rating;}
}
