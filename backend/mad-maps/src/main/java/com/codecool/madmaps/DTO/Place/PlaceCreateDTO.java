package com.codecool.madmaps.DTO.Place;

import java.util.List;

public record PlaceCreateDTO(String placeId, String name, List<String> placeTypes, double rating, int priceLevel, List<String> openingHours, List<String> photoReferences, double latitude, double longitude) {
}
