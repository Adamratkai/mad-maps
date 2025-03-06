package com.codecool.madmaps.DTO.Place;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlaceCreateDTO(String placeId, String name, List<String> placeTypes, double rating, int priceLevel, List<String> openingHours) {
}
