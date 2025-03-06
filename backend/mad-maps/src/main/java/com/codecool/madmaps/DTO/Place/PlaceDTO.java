package com.codecool.madmaps.DTO.Place;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlaceDTO(String placeId, String name, double rating, double priceLevel, List<String> openingHours) {
}
