package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.Trip.TripCreateDTO;
import com.codecool.madmaps.DTO.Trip.TripDTO;
import com.codecool.madmaps.DTO.Trip.TripUpdateDTO;
import com.codecool.madmaps.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/")
    public List<TripDTO> getAllTrips() {
        return tripService.getTrips();
    }

    @GetMapping("/{tripId}")
    public TripDTO getTripById(@PathVariable UUID tripId) {
        return tripService.getTripById(tripId);
    }

    @PutMapping("/{tripId}")
    public boolean updateTrip(@PathVariable UUID tripId, @RequestBody TripUpdateDTO tripUpdateDTO) {
        return tripService.updateTrip(tripId, tripUpdateDTO);
    }
}
