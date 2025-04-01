package com.codecool.madmaps.DTO.Recommendation;

import java.util.List;

public record DetailedPlaceDTO(String name, double rating, int price_level, String place_id, List<PlacePhotoDTO> photos, List<String> types, PlaceOpeningHours opening_hours, Geometry geometry) {
}
