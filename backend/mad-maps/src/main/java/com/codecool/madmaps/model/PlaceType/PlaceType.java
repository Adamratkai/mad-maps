package com.codecool.madmaps.model.PlaceType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "place_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeType;
}
