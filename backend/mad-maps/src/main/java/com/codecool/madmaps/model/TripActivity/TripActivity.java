package com.codecool.madmaps.model.TripActivity;

import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trip_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private UUID tripId;

    @PrePersist
    private void onCreate() {
        this.tripId = UUID.randomUUID();
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private LocalDateTime visitTime;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
