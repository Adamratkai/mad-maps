package com.codecool.madmaps.model.Place;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.http.WebSocket;
import java.util.List;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private List<PlaceType> placeTypes;
    private double rating;
    private int priceLevel;
    private List<PlaceImage> images;
    private List<OpeningHours> openingHours;

    @Transient
    public double calculateScore() {
        //TODO
        return 0;
    }

}
