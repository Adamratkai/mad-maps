package com.codecool.madmaps.security.service;

import com.codecool.madmaps.model.Role.Role;
import com.codecool.madmaps.model.Traveller;
import com.codecool.madmaps.repository.TravellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TravellerRepository travellerRepository;

    @Autowired
    public UserDetailsServiceImpl(TravellerRepository travellerRepository) {
        this.travellerRepository = travellerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Traveller userEntity = travellerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : userEntity.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRoleType().toString()));
        }

        return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
    }
}
