package com.codecool.madmaps.DTO.Trip;

import java.time.LocalDateTime;

public record TripActivityCreateDTO(String placeId, LocalDateTime visitTime) {
}
