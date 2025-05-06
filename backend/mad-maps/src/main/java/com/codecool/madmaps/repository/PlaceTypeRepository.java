package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {
    Optional<PlaceType> findByPlaceType(String placeType);
}
