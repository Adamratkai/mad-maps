package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.model.Place.Place;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
                    place.getPlaceId(),
                    place.getName(),
                    place.getPlaceTypes(),
                    place.getRating(),
                    place.getPriceLevel(),
                    place.getOpeningHours()));
        }
        return placeDTOs;
    }

    public PlaceDTO getPlaceById(String placeId) {
        Place place = places.stream().filter(pl -> pl.getPlaceId().equals(placeId)).findFirst().orElseThrow(NoSuchElementException::new);
        return new PlaceDTO(
                place.getPlaceId(),
                place.getName(),
                place.getPlaceTypes(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours());
    }

    public PlaceDTO createPlace(PlaceCreateDTO placeCreateDTO) {
        Place place = new Place();
        place.setPlaceId(placeCreateDTO.placeId());
        place.setName(placeCreateDTO.name());
        place.setPlaceTypes(placeCreateDTO.placeTypes());
        place.setRating(placeCreateDTO.rating());
        place.setPriceLevel(placeCreateDTO.priceLevel());
        place.setOpeningHours(placeCreateDTO.openingHours());
        this.places.add(place);
        return new PlaceDTO(place.getPlaceId(),
                place.getName(),
                place.getPlaceTypes(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours());
    }
}
