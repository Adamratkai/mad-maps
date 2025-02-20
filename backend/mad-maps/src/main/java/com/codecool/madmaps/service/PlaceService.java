package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.model.Place.Place;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PlaceService {

    List<Place> places;

    public PlaceService(List<Place> places) {
        this.places = places;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<PlaceDTO> placeDTOs = new ArrayList<>();
        for (Place place : places) {
            placeDTOs.add(new PlaceDTO(
                    place.publicId(),
                    place.name(),
                    place.rating(),
                    place.price(),
                    place.img(),
                    place.tripId(),
                    place.time()
            ));
        }
        return placeDTOs;
    }

    public PlaceDTO getPlaceById(UUID placeId) {
        Place place = places.stream().filter(pl -> pl.publicId().equals(placeId)).findFirst().orElseThrow(NoSuchElementException::new);
        return new PlaceDTO(
                place.publicId(),
                place.name(),
                place.rating(),
                place.price(),
                place.img(),
                place.tripId(),
                place.time()
        );
    }

    public PlaceDTO createPlace(PlaceCreateDTO placeCreateDTO) {
        Place place = new Place(UUID.randomUUID(), placeCreateDTO.name(), placeCreateDTO.rating(), placeCreateDTO.price(), placeCreateDTO.img(), placeCreateDTO.tripId(), placeCreateDTO.time());
        this.places.add(place);
        return new PlaceDTO(place.publicId(), place.name(), place.rating(), place.price(), place.img(), place.tripId(), place.time());
    }
}
