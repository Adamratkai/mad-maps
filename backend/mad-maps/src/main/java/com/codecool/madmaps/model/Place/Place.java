package com.codecool.madmaps.model.Place;

import java.time.LocalDateTime;
import java.util.UUID;

public record Place(UUID publicId, String name, double rating, double price, String img, UUID tripId, LocalDateTime time) {
}
