package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDetailsDTO(UUID tripId, String tripName, LocalDate startDate, LocalDate endDate, List<TripActivityDTO> tripActivities) {
}
