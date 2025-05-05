package com.codecool.madmaps.service;


import com.codecool.madmaps.DTO.Jwt.JwtDTO;
import com.codecool.madmaps.DTO.Traveller.CreateUserDTO;
import com.codecool.madmaps.DTO.Traveller.UserDTO;
import com.codecool.madmaps.model.Role.Role;
import com.codecool.madmaps.model.Role.RoleType;
import com.codecool.madmaps.model.Traveler.Traveller;
import com.codecool.madmaps.repository.RoleRepository;
import com.codecool.madmaps.repository.TravellerRepository;
import com.codecool.madmaps.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TravellerService {

    private final PasswordEncoder encoder;
    private final TravellerRepository travellerRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public TravellerService(PasswordEncoder encoder, TravellerRepository travellerRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.encoder = encoder;
        this.travellerRepository = travellerRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Traveller getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return travellerRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No authenticated user");
    }


    public void registerUser(CreateUserDTO signUpRequest) {
        Role role = roleRepository.findByRoleType(RoleType.ROLE_USER).get();
        Traveller user = new Traveller();
        user.setUserName(signUpRequest.username());
        user.setPassword(encoder.encode(signUpRequest.password()));
        user.setEmail(signUpRequest.email());
        user.setRoles(Set.of(role));
        travellerRepository.save(user);
    }

    public JwtDTO loginUser(UserDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.email(),
                loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtDTO(jwtToken, userDetails.getUsername(), roles);

    }
}
