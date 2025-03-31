package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.Traveler.Traveller;
import com.codecool.madmaps.model.Trip.Trip;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @EntityGraph(attributePaths = {"tripActivities"})
    Optional<Trip> findByTripId(UUID tripId);
    int deleteByTripId(UUID tripId);
    List<Trip> findByTraveller(Traveller traveller);
}
