package com.codecool.madmaps.DTO.Place;

import com.codecool.madmaps.DTO.Recommendation.Location;

import java.util.List;
import java.util.UUID;

public record PlaceDTO(String placeId, String name, double rating, int priceLevel, List<String> openingHours, List<UUID> photos, Location location) {
}
