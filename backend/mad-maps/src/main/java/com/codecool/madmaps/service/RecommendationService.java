package com.codecool.madmaps.service;

import com.codecool.madmaps.DTO.Recommendation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate;
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    public RecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RecommendationDTO> getRecommendations(String location, String type) {
        String radius = "500";
        int maxResults = 5;
        String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%s&type=%s&rankby=prominence&key=%s", location, radius, type, googleMapsApiKey);
        RecommendationResultDTO response = restTemplate.getForObject(url, RecommendationResultDTO.class);
        return response.results().stream().limit(maxResults).collect(Collectors.toList());
    }

    public DetailedPlaceWithImgDTO getDetailedPlace(String place_id) {
        String fields = "name,place_id,rating,price_level,photos,opening_hours/weekday_text,types";
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?fields=%s&place_id=%s&key=%s",
                fields, place_id, googleMapsApiKey);
        DetailedPlaceResultDTO response = restTemplate.getForObject(url, DetailedPlaceResultDTO.class);
        DetailedPlaceDTO detailedPlace = response.result();
        return new DetailedPlaceWithImgDTO(detailedPlace, getPhoto(detailedPlace.photos().get(0).photo_reference()));
    }

    private String getPhoto(String photo_reference) {
        String url = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=%s&key=%s", photo_reference, googleMapsApiKey);
        byte[] result = restTemplate.getForObject(url, byte[].class);
        return Base64.getEncoder().encodeToString(result);
    }

}
