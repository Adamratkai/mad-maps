package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.Place;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @EntityGraph(attributePaths = "photos")
    Optional<Place> findByPlaceId(String placeId);
}
