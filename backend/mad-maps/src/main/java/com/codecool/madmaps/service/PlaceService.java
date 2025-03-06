package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.PlaceType.PlaceType;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.PlaceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceTypeRepository placeTypeRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceTypeRepository placeTypeRepository) {
        this.placeRepository = placeRepository;
        this.placeTypeRepository = placeTypeRepository;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = this.placeRepository.findAll();
        return places.stream().map(place -> new PlaceDTO(
                place.getPlaceId(),
                place.getName(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours())).collect(Collectors.toList());
    }

    public PlaceDTO getPlaceById(String placeId) {
       Place place = this.placeRepository.findByPlaceId(placeId).orElseThrow(NoSuchElementException::new);
        return new PlaceDTO(
                place.getPlaceId(),
                place.getName(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours());
    }

    public PlaceDTO createPlace(PlaceCreateDTO placeCreateDTO) {
        Place place = new Place();
        Set<PlaceType> placeTypes = placeCreateDTO.placeTypes().stream().map(this::findOrCreatePlaceType).collect(Collectors.toSet());
        place.setPlaceId(placeCreateDTO.placeId());
        place.setName(placeCreateDTO.name());
        place.setPlaceTypes(placeTypes);
        place.setRating(placeCreateDTO.rating());
        place.setPriceLevel(placeCreateDTO.priceLevel());
        place.setOpeningHours(placeCreateDTO.openingHours());
        this.placeRepository.save(place);
        return new PlaceDTO(place.getPlaceId(),
                place.getName(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours());
    }

    private PlaceType findOrCreatePlaceType(String placeType) {
        return this.placeTypeRepository.findByPlaceType(placeType).orElseGet(() -> {
                PlaceType newPlaceType = new PlaceType();
                newPlaceType.setPlaceType(placeType);
                return this.placeTypeRepository.save(newPlaceType);
        });
    }
}
