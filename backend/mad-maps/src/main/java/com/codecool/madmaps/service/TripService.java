package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Trip.TripCreateDTO;
import com.codecool.madmaps.DTO.Trip.TripDTO;
import com.codecool.madmaps.DTO.Trip.TripUpdateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayCreateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayDTO;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.*;

@Service
public class TripService {

    private Set<Trip> trips;
    private TripDayService tripDayService;

    public TripService(TripDayService tripDayService) {
        trips = new HashSet<>();
        this.tripDayService = tripDayService;
    }

    public TripDTO createTrip(TripCreateDTO tripCreateDTO) {
        Trip newTrip = new Trip(
                UUID.randomUUID(),
                tripCreateDTO.name().isBlank() ? tripCreateDTO.startDate().toString() + " - " + tripCreateDTO.endDate().toString() : tripCreateDTO.name(),
                new ArrayList<>(),
                tripCreateDTO.startDate(),
                tripCreateDTO.endDate()

        );
        addTripDays(tripCreateDTO, newTrip);
        trips.add(newTrip);
        return new TripDTO(
                newTrip.publicId(),
                newTrip.name(),
                newTrip.tripDayIds(),
                newTrip.startDate(),
                newTrip.endDate()
        );
    }

    private void addTripDays(TripCreateDTO tripCreateDTO, Trip newTrip) {
        Period period = Period.between(tripCreateDTO.startDate(), tripCreateDTO.endDate());
        for (int i = 0; i <= period.getDays(); i++) {
            TripDayDTO tripDayDTO = tripDayService.createTripDay(new TripDayCreateDTO(
                            tripCreateDTO.startDate().plusDays(i),
                            new ArrayList<>()
                    )
            );
            newTrip.tripDayIds().add(tripDayDTO.publicId());
        }
    }

    public List<TripDTO> getTrips() {
        List<TripDTO> tripDTOList = new ArrayList<>();
        for (Trip trip : trips) {
            tripDTOList.add(new TripDTO(
                    trip.publicId(),
                    trip.name(),
                    trip.tripDayIds(),
                    trip.startDate(),
                    trip.endDate()

            ));
        }
        return tripDTOList;
    }

    public TripDTO getTripById(UUID tripId) {
        Trip trip = trips.stream().filter(t -> t.publicId().equals(tripId)).findFirst().orElseThrow(NoSuchElementException::new);
        return new TripDTO(
                trip.publicId(),
                trip.name(),
                trip.tripDayIds(),
                trip.startDate(),
                trip.endDate()
        );
    }

    public boolean updateTrip(UUID tripId, TripUpdateDTO tripUpdateDTO) {
        Trip trip = trips.stream().filter(t -> t.publicId().equals(tripId)).findFirst().orElseThrow(NoSuchElementException::new);
        trips.remove(trip);
        return trips.add(new Trip(
                tripId,
                tripUpdateDTO.name(),
                tripUpdateDTO.tripDays().stream().map(TripDayDTO::publicId).toList(),
                tripUpdateDTO.startDate(),
                tripUpdateDTO.endDate()
        ));
    }
}
