package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Trip.TripCreateDTO;
import com.codecool.madmaps.DTO.Trip.TripDTO;
import com.codecool.madmaps.model.Trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class TripService {

    private Set<Trip> trips;

    public TripService() {
        trips = new HashSet<>();
    }

    public TripDTO createTrip(TripCreateDTO tripCreateDTO) {
        Trip newTrip = new Trip(
                UUID.randomUUID(),
                tripCreateDTO.name().isBlank() ? tripCreateDTO.startDate().toString() + " - " + tripCreateDTO.endDate().toString() : tripCreateDTO.name(),
                new ArrayList<>(),
                tripCreateDTO.startDate(),
                tripCreateDTO.endDate()

        );
        trips.add(newTrip);
        return new TripDTO(
                newTrip.publicId(),
                newTrip.name(),
                new ArrayList<>(),
                newTrip.startDate(),
                newTrip.endDate()
        );
    }
}
