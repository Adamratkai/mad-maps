package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayCreateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayDTO;
import com.codecool.madmaps.model.TripDay.TripDay;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TripDayService {

    private List<TripDay> tripDays;
    private final PlaceService placeService;

    public TripDayService(PlaceService placeService) {
        this.tripDays = new ArrayList<>();
        this.placeService = placeService;
    }

    public TripDayDTO createTripDay(TripDayCreateDTO tripDayDTO) {
        TripDay newTripDay = new TripDay(UUID.randomUUID(), tripDayDTO.date(), new ArrayList<>(tripDayDTO.placeIds()));
        tripDays.add(newTripDay);
        List<PlaceDTO> places = newTripDay.placeIds().stream().map(this.placeService::getPlaceById).toList();
        return new TripDayDTO(newTripDay.publicId(), newTripDay.date(), places);
    }

    public TripDayDTO getTripDayById(UUID publicId) {
        TripDay tripDay = this.tripDays.stream().filter(tDay -> tDay.publicId().equals(publicId)).findFirst().orElseThrow(NoSuchElementException::new);
        List<PlaceDTO> places = tripDay.placeIds().stream().map(this.placeService::getPlaceById).toList();
        return new TripDayDTO(tripDay.publicId(), tripDay.date(), places);
    }

    public List<TripDayDTO> getAllTripDays() {
        return this.tripDays.stream().map(currentDay -> new TripDayDTO(
                        currentDay.publicId(),
                        currentDay.date(),
                        currentDay.placeIds().stream().map(this.placeService::getPlaceById)
                                .toList()))
                .toList();
    }
}
