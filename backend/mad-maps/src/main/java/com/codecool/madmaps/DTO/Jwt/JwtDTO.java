package com.codecool.madmaps.DTO.Jwt;

import java.util.List;

public record JwtDTO(String token, String username, List<String> roles) {}
