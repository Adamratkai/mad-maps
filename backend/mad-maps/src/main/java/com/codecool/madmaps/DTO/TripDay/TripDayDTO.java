package com.codecool.madmaps.DTO.TripDay;

import com.codecool.madmaps.model.Place.Place;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDayDTO(UUID publicId, LocalDate date,  List<Place> places) {
}
