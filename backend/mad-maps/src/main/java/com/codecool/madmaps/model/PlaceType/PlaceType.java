package com.codecool.madmaps.model.PlaceType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "place_types")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String placeType;
}
