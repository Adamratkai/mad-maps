package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.TripDay.TripDayCreateDTO;
import com.codecool.madmaps.DTO.TripDay.TripDayDTO;
import com.codecool.madmaps.model.TripDay.TripDay;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripDayService {

    private List<TripDay> tripDays;

    public TripDayService() {
        this.tripDays = new ArrayList<>();
    }

    public TripDayDTO createTripDay(TripDayCreateDTO tripDayDTO) {
        TripDay newTripDay = new TripDay(UUID.randomUUID(), new ArrayList<>(tripDayDTO.placeIds()));
        tripDays.add(newTripDay);
        return new TripDayDTO(newTripDay.publicId(), newTripDay.placeIds());
    }
}
