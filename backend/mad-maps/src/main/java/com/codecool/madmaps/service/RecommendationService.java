package com.codecool.madmaps.service;

import com.codecool.madmaps.DTO.Recommendation.RecommendationDTO;
import com.codecool.madmaps.DTO.Recommendation.RecommendationResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%s&type=%s&rankby=prominence&key=%s", location, radius, type, googleMapsApiKey);
        RecommendationResultDTO response = restTemplate.getForObject(url, RecommendationResultDTO.class);
        return new ArrayList<>(response.results());
    }

}
