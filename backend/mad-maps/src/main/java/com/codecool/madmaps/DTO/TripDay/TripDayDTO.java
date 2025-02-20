package com.codecool.madmaps.DTO.TripDay;

import com.codecool.madmaps.DTO.Place.PlaceDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDayDTO(UUID publicId, LocalDate date,  List<PlaceDTO> places) {
}
