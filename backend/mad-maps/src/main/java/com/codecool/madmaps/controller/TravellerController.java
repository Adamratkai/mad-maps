package com.codecool.madmaps.controller;

import com.codecool.madmaps.DTO.Jwt.JwtResponse;
import com.codecool.madmaps.model.payload.CreateUserRequest;
import com.codecool.madmaps.model.payload.UserRequest;
import com.codecool.madmaps.security.jwt.JwtUtils;
import com.codecool.madmaps.service.TravellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/traveller")
public class TravellerController {

    private final TravellerService travellerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public TravellerController(TravellerService travellerService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.travellerService = travellerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest signUpRequest) {
        travellerService.registerUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody UserRequest loginRequest) {
        return travellerService.loginUser(loginRequest);
    }

}
