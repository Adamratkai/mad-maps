package com.codecool.madmaps.DTO.Jwt;

import java.util.List;

public record JwtResponse(String token, String username, List<String> roles) {}
