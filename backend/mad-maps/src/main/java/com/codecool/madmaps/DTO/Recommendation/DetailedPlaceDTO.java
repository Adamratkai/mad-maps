package com.codecool.madmaps.DTO.Recommendation;

import java.util.List;

public record DetailedPlaceDTO(String name, String rating, String price_level, String place_id, List<PlacePhotoDTO> photos, List<String> types, PlaceOpeningHours opening_hours ) {
}
