package com.codecool.madmaps.model.Place;

import com.codecool.madmaps.model.OpeningHours.OpeningHours;
import com.codecool.madmaps.model.PlaceType.PlaceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
    private String placeId;


    @Column(nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(name = "place_place_type", joinColumns = @JoinColumn(name = "place_id"), inverseJoinColumns = @JoinColumn(name = "place_type_id"))
    private Set<PlaceType> placeTypes;

    private double rating;

    private int priceLevel;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opening_hours_id")
    private OpeningHours openingHours;

    @Transient
    public double calculateScore() {
        //TODO
        return 0;
    }

}
