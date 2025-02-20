package com.codecool.madmaps.controller;


import com.codecool.madmaps.DTO.TripDay.TripDayCreateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayDTO;
import com.codecool.madmaps.service.TripDayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tripdays")
public class TripDayController {
    private final TripDayService tripDayService;

    public TripDayController(TripDayService tripDayService) {
        this.tripDayService = tripDayService;
    }

    @PostMapping("/")
    public TripDayCreateDTO createTripDay(@RequestBody TripDayCreateDTO newTripDay) {
        this.tripDayService.createTripDay(newTripDay);
        return newTripDay;
    }
}
