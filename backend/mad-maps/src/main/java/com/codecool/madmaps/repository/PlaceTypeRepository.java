package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.PlaceType.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {
}
