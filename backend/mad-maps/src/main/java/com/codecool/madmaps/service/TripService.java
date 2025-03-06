package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Trip.TripCreateDTO;
import com.codecool.madmaps.DTO.Trip.TripDTO;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.Trip.Trip;
import com.codecool.madmaps.model.TripActivity.TripActivity;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final PlaceRepository placeRepository;

    public TripService(TripRepository tripRepository, PlaceRepository placeRepository) {
        this.tripRepository = tripRepository;
        this.placeRepository = placeRepository;
    }

    public UUID createTrip(TripCreateDTO tripCreateDTO) {
        Trip newTrip = new Trip();
        newTrip.setTripId(UUID.randomUUID());
        newTrip.setName(tripCreateDTO.name());
        newTrip.setStartDate(tripCreateDTO.startDate());
        newTrip.setEndDate(tripCreateDTO.endDate());
        return this.tripRepository.save(newTrip).getTripId();
    }

    private void addTripActivity(CreateTripActivityDTO createTripActivityDTO) {
        TripActivity newTripActivity = new TripActivity();
        newTripActivity.setVisitTime(createTripActivityDTO.visitTime());
        Trip trip = this.tripRepository.findByTripId(createTripActivityDTO.tripId()).findFirst().orElseThrow(() -> new NoSuchTripException());
        newTripActivity.setTrip(trip);
        Place place = this.placeRepository.findByPlaceId(createTripActivityDTO.placeId()).findFirst().orElseThrow(() -> new NoSuchPlaceException());
        newTripActivity.setPlace(place);
    }

    public List<TripDTO> getTrips() {
        List<Trip> trips = this.tripRepository.findAll();
        return trips.stream().map(this::convertTripToTripDTO).collect(Collectors.toList());
    }

    public TripDetailsDTO getTripById(UUID tripId) {
       Trip trip = this.tripRepository.findByTripId(tripId).findFirst().orElseThrow(() -> new NoSuchTripException());
       return convertTripToTripDetailsDTO(trip);

    }

    private TripDTO convertTripToTripDTO(Trip trip) {
        return new TripDTO(trip.getTripId(), trip.getName(), trip.getStartDate(), trip.getEndDate());
    }
    private TripDetailsDTO convertTripToTripDetailsDTO(Trip trip) {
        return new TripDTO(trip.getTripId(), trip.getStartDate(), trip.getEndDate(), trip.getTripActivities());
    }
    }
