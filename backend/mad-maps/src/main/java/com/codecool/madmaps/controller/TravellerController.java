package com.codecool.madmaps.controller;

import com.codecool.madmaps.DTO.Jwt.JwtResponse;
import com.codecool.madmaps.exceptions.UserAlreadyExistsException;
import com.codecool.madmaps.model.Role.Role;
import com.codecool.madmaps.model.Role.RoleType;
import com.codecool.madmaps.model.Traveler.Traveller;
import com.codecool.madmaps.model.payload.CreateUserRequest;
import com.codecool.madmaps.model.payload.UserRequest;
import com.codecool.madmaps.repository.RoleRepository;
import com.codecool.madmaps.repository.TravellerRepository;
import com.codecool.madmaps.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/traveller")
public class TravellerController {

    private final RoleRepository roleRepository;
    private final TravellerRepository travellerRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public TravellerController(RoleRepository roleRepository, TravellerRepository travellerRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.roleRepository = roleRepository;
        this.travellerRepository = travellerRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest signUpRequest) {
        Role role = roleRepository.findByRoleType(RoleType.ROLE_USER).get();
        Traveller user = new Traveller();
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(Set.of(role));
        travellerRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity
                .ok(new JwtResponse(jwtToken, userDetails.getUsername(), roles));
    }

}
