package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.OpeningHours.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
}
