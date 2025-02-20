package com.codecool.madmaps.DTO.TripDay;

import java.util.List;
import java.util.UUID;

public record TripDayDTO(UUID publicId, List<Integer> placeIds) {
}
