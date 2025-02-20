package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.TripDay.TripDayCreateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayDTO;
import com.codecool.madmaps.service.TripDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tripdays")
public class TripDayController {
    private final TripDayService tripDayService;

    public TripDayController(TripDayService tripDayService) {
        this.tripDayService = tripDayService;
    }

    @PostMapping("/")
    public TripDayDTO createTripDay(@RequestBody TripDayCreateDTO newTripDay) {
        return this.tripDayService.createTripDay(newTripDay);
    }

    @GetMapping("/{tripDayId}")
    public TripDayDTO getTripDayById(@PathVariable UUID tripDayId) {
        return this.tripDayService.getTripDayById(tripDayId);
    }

    @GetMapping("/")
    public List<TripDayDTO> getAllTripDays() {
        return this.tripDayService.getAllTripDays();
    }
}
