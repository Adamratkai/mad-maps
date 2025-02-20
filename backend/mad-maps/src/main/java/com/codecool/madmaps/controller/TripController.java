package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.Trip.TripCreateDTO;
import com.codecool.madmaps.DTO.Trip.TripDTO;
import com.codecool.madmaps.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/")
    public TripDTO createTrip(@RequestBody TripCreateDTO tripCreateDTO) {
        return tripService.createTrip(tripCreateDTO);
    }
}
