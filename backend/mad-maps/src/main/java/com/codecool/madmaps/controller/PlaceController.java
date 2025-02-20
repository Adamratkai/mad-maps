package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/")
    public List<PlaceDTO> getPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{placeId}")
    public PlaceDTO getPlace(@PathVariable UUID placeId) {
        return placeService.getPlaceById(placeId);
    }

    @PostMapping("/")
    public PlaceDTO createPlace(@RequestBody PlaceCreateDTO placeCreateDTO) {
        return placeService.createPlace(placeCreateDTO);
    }
}
