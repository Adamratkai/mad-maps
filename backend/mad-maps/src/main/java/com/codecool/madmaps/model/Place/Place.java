package com.codecool.madmaps.model.Place;

import com.codecool.madmaps.model.PlaceType.PlaceType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
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


    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> openingHours;

    @Transient
    public double calculateScore() {
        //TODO
        return 0;
    }

}
