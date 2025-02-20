package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/places")
public class PlaceController {

    PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/")
    public List<PlaceDTO> getPlaces() {
        return placeService.getAllPlaces();
    }
}
