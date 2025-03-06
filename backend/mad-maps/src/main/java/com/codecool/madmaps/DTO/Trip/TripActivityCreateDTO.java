package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TripActivityCreateDTO(UUID tripId, String placeId, LocalDateTime visitTime) {
}
