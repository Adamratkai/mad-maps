package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.DTO.Trip.*;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.Trip.Trip;
import com.codecool.madmaps.model.TripActivity.TripActivity;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    private void addTripActivity(TripActivityCreateDTO tripActivityCreateDTO) {
        TripActivity newTripActivity = new TripActivity();
        newTripActivity.setVisitTime(tripActivityCreateDTO.visitTime());
        Trip trip = this.tripRepository.findByTripId(tripActivityCreateDTO.tripId()).orElseThrow(NoSuchElementException::new);
        newTripActivity.setTrip(trip);
        Place place = this.placeRepository.findByPlaceId(tripActivityCreateDTO.placeId()).orElseThrow(NoSuchElementException::new);
        newTripActivity.setPlace(place);
    }

    public List<TripDTO> getTrips() {
        List<Trip> trips = this.tripRepository.findAll();
        return trips.stream().map(this::convertTripToTripDTO).collect(Collectors.toList());
    }

    public TripDetailsDTO getTripById(UUID tripId) {
       Trip trip = this.tripRepository.findByTripId(tripId).orElseThrow(NoSuchElementException::new);
       return convertTripToTripDetailsDTO(trip);

    }

    private TripDTO convertTripToTripDTO(Trip trip) {
        return new TripDTO(trip.getTripId(), trip.getName(), trip.getStartDate(), trip.getEndDate());
    }
    private TripDetailsDTO convertTripToTripDetailsDTO(Trip trip) {
        List<TripActivity> tripActivities = trip.getTripActivities();
        List<TripActivityDTO> tripActivityDTOS = tripActivities.stream().map(this::convertTripActivityToTripActivityDTO).collect(Collectors.toList());
        return new TripDetailsDTO(trip.getTripId(), trip.getName(), trip.getStartDate(), trip.getEndDate(), tripActivityDTOS);
    }

    private TripActivityDTO convertTripActivityToTripActivityDTO(TripActivity tripActivity) {
        Place place = tripActivity.getPlace();
        PlaceDTO placeDTO = new PlaceDTO(place.getPlaceId(), place.getName(), place.getRating(), place.getPriceLevel(), place.getOpeningHours().getOpeningHoursPerWeekDays());
        return new TripActivityDTO(placeDTO, tripActivity.getVisitTime());
    }
    }
