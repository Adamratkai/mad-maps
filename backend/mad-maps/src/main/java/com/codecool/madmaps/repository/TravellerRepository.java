package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.Traveler.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, Long> {
    Optional<Traveller> findByUserName(String username);
}
