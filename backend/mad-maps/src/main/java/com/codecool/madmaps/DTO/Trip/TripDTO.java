package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDate;
import java.util.UUID;

public record TripDTO(UUID tripId, String name, LocalDate startDate, LocalDate endDate) {
}
