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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravellerServiceTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private TravellerRepository travellerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private TravellerService travellerService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAuthenticatedUser_AuthenticatedUserFound() {
        UserDetails userDetails = new User("test@example.com", "password", Collections.emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Traveller expectedTraveller = new Traveller();
        when(travellerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(expectedTraveller));

        Traveller result = travellerService.getAuthenticatedUser();
        assertEquals(expectedTraveller, result);
    }

    @Test
    void getAuthenticatedUser_UserNotFoundThrowsException() {
        UserDetails userDetails = new User("test@example.com", "password", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null)
        );

        when(travellerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> travellerService.getAuthenticatedUser());
    }

    @Test
    void getAuthenticatedUser_NoAuthenticationThrowsException() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("nonUserDetails", null)
        );

        assertThrows(RuntimeException.class, () -> travellerService.getAuthenticatedUser());
    }

    @Test
    void registerUser_SavesUserWithEncodedPassword() {
        CreateUserDTO request = new CreateUserDTO("user", "password", "user@example.com");
        Role role = new Role();
        role.setRoleType(RoleType.ROLE_USER);

        when(roleRepository.findByRoleType(RoleType.ROLE_USER)).thenReturn(Optional.of(role));
        when(encoder.encode("password")).thenReturn("encodedPassword");

        travellerService.registerUser(request);

        verify(travellerRepository).save(argThat(user ->
                user.getUserName().equals("user") &&
                        user.getEmail().equals("user@example.com") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRoles().contains(role)
        ));
    }

    @Test
    void registerUser_RoleNotFoundThrowsException() {
        CreateUserDTO request = new CreateUserDTO("user", "password", "user@example.com");
        when(roleRepository.findByRoleType(RoleType.ROLE_USER)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> travellerService.registerUser(request));
    }

    @Test
    void registerUser_PasswordIsEncoded() {
        CreateUserDTO request = new CreateUserDTO("user", "password", "user@example.com");
        when(roleRepository.findByRoleType(RoleType.ROLE_USER)).thenReturn(Optional.of(new Role()));
        when(encoder.encode("password")).thenReturn("encodedPassword");

        travellerService.registerUser(request);

        verify(encoder).encode("password");
    }

    @Test
    void loginUser_ValidCredentialsReturnsJwtResponse() {
        UserDTO request = new UserDTO("user@example.com", "password");
        Authentication auth = mock(Authentication.class);
        User userDetails = new User(
                "user@example.com",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(auth)).thenReturn("testToken");

        JwtDTO response = travellerService.loginUser(request);

        assertEquals("testToken", response.token());
        assertEquals("user@example.com", response.username());
        assertTrue(response.roles().contains("ROLE_USER"));
    }

    @Test
    void loginUser_InvalidCredentialsThrowsException() {
        UserDTO request = new UserDTO("user@example.com", "wrong");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> travellerService.loginUser(request));
    }

    @Test
    void loginUser_RolesMappedCorrectlyInResponse() {
        UserDTO request = new UserDTO("user@example.com", "password");
        Authentication auth = mock(Authentication.class);
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );
        User userDetails = new User("user@example.com", "password", authorities);

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(auth)).thenReturn("testToken");

        JwtDTO response = travellerService.loginUser(request);

        assertTrue(response.roles().containsAll(List.of("ROLE_USER", "ROLE_ADMIN")));
    }
}