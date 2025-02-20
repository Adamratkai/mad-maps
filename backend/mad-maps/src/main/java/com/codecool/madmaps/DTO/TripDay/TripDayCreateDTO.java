package com.codecool.madmaps.DTO.TripDay;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDayCreateDTO(LocalDate date,  List<UUID> placeIds) {
}
