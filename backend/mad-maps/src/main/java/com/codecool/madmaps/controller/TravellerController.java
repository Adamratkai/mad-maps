package com.codecool.madmaps.controller;

import com.codecool.madmaps.DTO.Jwt.JwtDTO;
import com.codecool.madmaps.DTO.Traveller.CreateUserDTO;
import com.codecool.madmaps.DTO.Traveller.UserDTO;
import com.codecool.madmaps.service.TravellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/traveller")
public class TravellerController {

    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO signUpRequest) {
        travellerService.registerUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public JwtDTO loginUser(@RequestBody UserDTO loginRequest) {
        return travellerService.loginUser(loginRequest);
    }

}
