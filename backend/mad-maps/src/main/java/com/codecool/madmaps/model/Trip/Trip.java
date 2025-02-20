package com.codecool.madmaps.model.Trip;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Trip(UUID publicId, String name, List<UUID> tripDayIds, LocalDate startDate, LocalDate endDate) {
}
