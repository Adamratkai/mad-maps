package com.codecool.madmaps.model.TripDay;

import java.util.List;
import java.util.UUID;

public record TripDay(UUID publicId, List<Integer> placeIds) {
}
