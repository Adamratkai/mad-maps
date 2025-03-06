package com.codecool.madmaps.model.PlaceType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "place_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeType;
}
