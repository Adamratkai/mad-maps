package com.codecool.madmaps.DTO.Trip;

import com.codecool.madmaps.DTO.TripDay.TripDayDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDTO(UUID publicId, LocalDate startDate, LocalDate endDate) {
}
