package com.codecool.madmaps.DTO.Place;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlaceCreateDTO(String name, double rating, double price, String img, UUID tripId, LocalDateTime time) {
}
