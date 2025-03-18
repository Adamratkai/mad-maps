package com.codecool.madmaps.service;


import com.codecool.madmaps.model.Traveler.Traveller;
import com.codecool.madmaps.repository.TravellerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TravellerService {

    private final TravellerRepository travellerRepository;


    public TravellerService(TravellerRepository travellerRepository) {
        this.travellerRepository = travellerRepository;
    }

    public Traveller getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return travellerRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No authenticated user");
    }
}
