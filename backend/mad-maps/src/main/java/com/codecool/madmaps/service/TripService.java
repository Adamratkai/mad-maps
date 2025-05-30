package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.DTO.Recommendation.Location;
import com.codecool.madmaps.DTO.Trip.*;
import com.codecool.madmaps.model.Photo;
import com.codecool.madmaps.model.Place;
import com.codecool.madmaps.model.Traveller;
import com.codecool.madmaps.model.Trip;
import com.codecool.madmaps.model.TripActivity;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.TravellerRepository;
import com.codecool.madmaps.repository.TripActivityRepository;
import com.codecool.madmaps.repository.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final PlaceRepository placeRepository;
    private final TripActivityRepository tripActivityRepository;
    private final TravellerService travellerService;

    public TripService(TripRepository tripRepository, PlaceRepository placeRepository, TripActivityRepository tripActivityRepository, TravellerRepository travellerRepository, TravellerService travellerService) {
        this.tripRepository = tripRepository;
        this.placeRepository = placeRepository;
        this.tripActivityRepository = tripActivityRepository;
        this.travellerService = travellerService;
    }

    public UUID createTrip(TripCreateDTO tripCreateDTO) {
        Traveller traveller = travellerService.getAuthenticatedUser();
        Trip newTrip = new Trip();
        newTrip.setTripId(UUID.randomUUID());
        newTrip.setName(tripCreateDTO.name());
        newTrip.setStartDate(tripCreateDTO.startDate());
        newTrip.setEndDate(tripCreateDTO.endDate());
        newTrip.setTraveller(traveller);
        return this.tripRepository.save(newTrip).getTripId();
    }


    @Transactional
    public UUID addTripActivity(UUID tripId, TripActivityCreateDTO tripActivityCreateDTO) {
        TripActivity newTripActivity = new TripActivity();
        newTripActivity.setVisitTime(tripActivityCreateDTO.visitTime());
        Trip trip = this.tripRepository.findByTripId(tripId).orElseThrow(() -> new NoSuchElementException("Trip not found"));
        newTripActivity.setTrip(trip);
        Place place = this.placeRepository.findByPlaceId(tripActivityCreateDTO.placeId()).orElseThrow(() -> new NoSuchElementException("Place not found"));
        newTripActivity.setPlace(place);
        return this.tripActivityRepository.save(newTripActivity).getTripActivityId();
    }


    public int deleteTripActivityById(UUID tripActivityId) {
        return this.tripActivityRepository.deleteByTripActivityId(tripActivityId);
    }

    public List<TripDTO> getTrips() {
        List<Trip> trips = this.tripRepository.findAll();
        return trips.stream().map(this::convertTripToTripDTO).collect(Collectors.toList());
    }

    public TripDetailsDTO getTripById(UUID tripId) {
       Trip trip = this.tripRepository.findByTripId(tripId).orElseThrow(NoSuchElementException::new);
       return convertTripToTripDetailsDTO(trip);

    }

    public int deleteTripById(UUID tripId) {
        return this.tripRepository.deleteByTripId(tripId);
    }

    @Transactional
    public void updateTripById(UUID tripId, TripUpdateDTO tripUpdateDTO) {
        Trip trip = this.tripRepository.findByTripId(tripId).orElseThrow(() -> new NoSuchElementException("Trip not found"));
        trip.setName(tripUpdateDTO.name());
        trip.setStartDate(tripUpdateDTO.startDate());
        trip.setEndDate(tripUpdateDTO.endDate());
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
        Location location = new Location(place.getLatitude(), place.getLongitude());
        PlaceDTO placeDTO = new PlaceDTO(place.getPlaceId(), place.getName(), place.getRating(), place.getPriceLevel(), place.getOpeningHours(),
                place.getPhotos().stream().map(Photo::getPhotoId).collect(Collectors.toList()),
                location);
        return new TripActivityDTO(placeDTO, tripActivity.getVisitTime());
    }

    public List<TripDTO> getTripsByTraveller() {
        Traveller traveller = this.travellerService.getAuthenticatedUser();
        List<Trip> trips = this.tripRepository.findByTraveller(traveller);
        return trips.stream().map(this::convertTripToTripDTO).collect(Collectors.toList());
    }
}
