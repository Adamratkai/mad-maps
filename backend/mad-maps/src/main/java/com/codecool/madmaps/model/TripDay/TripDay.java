package com.codecool.madmaps.model.TripDay;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripDay(UUID publicId, LocalDate date, List<Integer> placeIds) {
}
