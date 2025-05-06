package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.TripActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripActivityRepository extends JpaRepository<TripActivity, Long> {
    int deleteByTripActivityId(UUID tripActivityId);
}
