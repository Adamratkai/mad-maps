package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.TripActivity.TripActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripActivityRepository extends JpaRepository<TripActivity, Long> {
}
