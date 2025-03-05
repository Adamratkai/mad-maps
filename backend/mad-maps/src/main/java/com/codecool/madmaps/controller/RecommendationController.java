package com.codecool.madmaps.controller;

import com.codecool.madmaps.DTO.Recommendation.DetailedPlaceWithImgDTO;
import com.codecool.madmaps.DTO.Recommendation.RecommendationDTO;
import com.codecool.madmaps.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;


    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/")
    public List<RecommendationDTO> getRecommendations(@RequestParam String location, @RequestParam String type) {
        return recommendationService.getRecommendations(location, type);
    }

    @GetMapping("/detailed")
    public DetailedPlaceWithImgDTO getDetailedPlace(@RequestParam String place_id) {
        return recommendationService.getDetailedPlace(place_id);
    }

}
