package com.codecool.madmaps.controller;

import com.codecool.madmaps.DTO.Trip.TripActivityCreateDTO;
import com.codecool.madmaps.model.TripActivity.TripActivity;
import com.codecool.madmaps.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/trip-activities")
public class TripActivityController {

    private final TripService tripService;

    public TripActivityController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/{tripId}")
    public UUID addTripActivity(@PathVariable UUID tripId, @RequestBody TripActivityCreateDTO tripActivityCreateDTO) {
        return tripService.addTripActivity(tripActivityCreateDTO);
    }

    @DeleteMapping("/{tripActivityId}")
    public boolean deleteTripActivity(@PathVariable UUID tripActivityId) {
        return tripService.deleteTripActivity(tripActivityId);
    }
}
