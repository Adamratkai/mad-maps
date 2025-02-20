package com.codecool.madmaps.DTO.TripDay;

import java.time.LocalDate;
import java.util.List;

public record TripDayCreateDTO(LocalDate date,  List<Integer> placeIds) {
}
