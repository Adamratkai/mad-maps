package com.codecool.madmaps.DTO.Trip;

import com.codecool.madmaps.DTO.Place.PlaceDTO;

import java.time.LocalDateTime;

public record TripActivityDTO(PlaceDTO placeDTO, LocalDateTime visitTime) {
}
