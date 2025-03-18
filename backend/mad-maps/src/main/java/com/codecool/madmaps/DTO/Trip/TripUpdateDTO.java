package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDate;

public record TripUpdateDTO(String name, LocalDate startDate, LocalDate endDate) {
}
