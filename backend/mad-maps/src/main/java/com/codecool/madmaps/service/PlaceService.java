package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.DTO.Recommendation.DetailedPlaceDTO;
import com.codecool.madmaps.DTO.Recommendation.DetailedPlaceResultDTO;
import com.codecool.madmaps.DTO.Recommendation.Location;
import com.codecool.madmaps.DTO.Recommendation.PlacePhotoDTO;
import com.codecool.madmaps.model.Photo.Photo;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.PlaceType.PlaceType;
import com.codecool.madmaps.repository.PhotoRepository;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.PlaceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceTypeRepository placeTypeRepository;
    private final PhotoRepository photoRepository;
    private final RestTemplate restTemplate;
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;
    private Logger log = LoggerFactory.getLogger(PlaceService.class);

    public PlaceService(PlaceRepository placeRepository, PlaceTypeRepository placeTypeRepository, PhotoRepository photoRepository, RestTemplate restTemplate) {
        this.placeRepository = placeRepository;
        this.placeTypeRepository = placeTypeRepository;
        this.photoRepository = photoRepository;
        this.restTemplate = restTemplate;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = this.placeRepository.findAll();
        return places.stream().map(place -> new PlaceDTO(
                place.getPlaceId(),
                place.getName(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours(),
                getIdsFromPhotos(place.getPhotos()),
                new Location(place.getLatitude(), place.getLongitude()))).collect(Collectors.toList());
    }

    public PlaceDTO getPlaceById(String placeId) {
       Place place = findOrCreatePlace(placeId);
       Location location = new Location(place.getLatitude(), place.getLongitude());
        return new PlaceDTO(
                place.getPlaceId(),
                place.getName(),
                place.getRating(),
                place.getPriceLevel(),
                place.getOpeningHours(),
                getIdsFromPhotos(place.getPhotos()),
                location);
    }

    public PlaceDTO createAndGetPlaceDTO(PlaceCreateDTO placeCreateDTO) {
        Place newPlace = createPlace(placeCreateDTO);
        Location location = new Location(newPlace.getLatitude(), newPlace.getLongitude());
        return new PlaceDTO(newPlace.getPlaceId(),
                newPlace.getName(),
                newPlace.getRating(),
                newPlace.getPriceLevel(),
                newPlace.getOpeningHours(),
                getIdsFromPhotos(newPlace.getPhotos()),
                location);
    }

    private Place createPlace(PlaceCreateDTO placeCreateDTO) {
        Place place = new Place();
        Set<PlaceType> placeTypes = placeCreateDTO.placeTypes().stream().map(this::findOrCreatePlaceType).collect(Collectors.toSet());
        place.setPlaceId(placeCreateDTO.placeId());
        place.setName(placeCreateDTO.name());
        place.setPlaceTypes(placeTypes);
        place.setRating(placeCreateDTO.rating());
        place.setPriceLevel(placeCreateDTO.priceLevel());
        place.setOpeningHours(placeCreateDTO.openingHours());
        place.setLatitude(placeCreateDTO.latitude());
        place.setLongitude(placeCreateDTO.longitude());
        Place savedPlace = this.placeRepository.save(place);
        List<Photo> savedPhotos = createPhotos(placeCreateDTO.photoReferences(), savedPlace);
        savedPlace.setPhotos(savedPhotos);
        return savedPlace;
    }

    public PlaceType findOrCreatePlaceType(String placeType) {
        return this.placeTypeRepository.findByPlaceType(placeType).orElseGet(() -> {
                PlaceType newPlaceType = new PlaceType();
                newPlaceType.setPlaceType(placeType);
                return this.placeTypeRepository.save(newPlaceType);
        });
    }

    public Place findOrCreatePlace(String placeId) {
        return this.placeRepository.findByPlaceId(placeId).orElseGet(() -> {
            String fields = "name,place_id,rating,price_level,photos,opening_hours/weekday_text,types,geometry/location";
            String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?fields=%s&place_id=%s&key=%s",
                    fields, placeId, googleMapsApiKey);
            DetailedPlaceResultDTO response = restTemplate.getForObject(url, DetailedPlaceResultDTO.class);
            DetailedPlaceDTO detailedPlace = response.result();
            log.info(detailedPlace.toString());
            List<String> references = detailedPlace.photos().stream().map(PlacePhotoDTO::photo_reference).toList();
            PlaceCreateDTO placeCreateDTO = new PlaceCreateDTO(
                    detailedPlace.place_id(),
                    detailedPlace.name(),
                    detailedPlace.types(),
                    detailedPlace.rating(),
                    detailedPlace.price_level(),
                    detailedPlace.opening_hours().weekday_text(),
                    references,
                    detailedPlace.geometry().location().lat(),
                    detailedPlace.geometry().location().lng()
            );
           return createPlace(placeCreateDTO);
        });
    }

    private List<UUID> getIdsFromPhotos(List<Photo> photos) {
        return photos.stream().map(Photo::getPhotoId).collect(Collectors.toList());
    }

    public List<Photo> createPhotos(List<String> references, Place place) {
        List<Photo> photos = new ArrayList<>();
        int max_photos = 2;
        int counter = 0;
        for (String reference : references) {
            if (counter >= max_photos) {
                break;
            }
            Photo photo = new Photo();
            photo.setPhoto(getPhotoFromGoogle(reference));
            photo.setPlace(place);
            photoRepository.save(photo);
            photos.add(photo);
            counter++;
        }
        return photos;
    }

    private byte[] getPhotoFromGoogle(String photo_reference) {
        String url = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=%s&key=%s", photo_reference, googleMapsApiKey);
        return restTemplate.getForObject(url, byte[].class);
    }

    public byte[] getPhotoById(UUID photoId) {
        Photo photo = photoRepository.findByPhotoId(photoId).orElseThrow(() -> new NoSuchElementException("No photo with id " + photoId));
        return photo.getPhoto();
    }

    public List<UUID> getPhotoIdsByPlaceId(String placeId) {
        Place place = placeRepository.findByPlaceId(placeId).orElseThrow(() -> new NoSuchElementException("No place with id " + placeId));
        return getIdsFromPhotos(place.getPhotos());
    }
}
