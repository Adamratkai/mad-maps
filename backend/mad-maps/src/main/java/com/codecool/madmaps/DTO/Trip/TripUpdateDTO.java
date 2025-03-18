package com.codecool.madmaps.DTO.Trip;

import com.codecool.madmaps.DTO.TripDay.TripDayDTO;

import java.time.LocalDate;
import java.util.List;

public record TripUpdateDTO(String name, LocalDate startDate, LocalDate endDate) {
}
