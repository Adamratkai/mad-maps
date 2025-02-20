package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDate;

public record TripCreateDTO(String name, LocalDate startDate, LocalDate endDate) {
}
