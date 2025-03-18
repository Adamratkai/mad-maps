package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.Photo.Photo;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.PlaceType.PlaceType;
import com.codecool.madmaps.model.Trip.Trip;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByPlaceTypesContaining(PlaceType placeType);
    @EntityGraph(attributePaths = "photos")
    Optional<Place> findByPlaceId(String placeId);
}
