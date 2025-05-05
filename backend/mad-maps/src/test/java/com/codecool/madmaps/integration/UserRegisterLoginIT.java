package com.codecool.madmaps.integration;

import com.codecool.madmaps.DTO.Jwt.JwtDTO;
import com.codecool.madmaps.DTO.Traveller.CreateUserDTO;
import com.codecool.madmaps.DTO.Traveller.UserDTO;
import com.codecool.madmaps.repository.TravellerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Testcontainers
public class UserRegisterLoginIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TravellerRepository travellerRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("test")
            .withUsername("testUser")
            .withPassword("testPassword");

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "password123";
    private final String TEST_USERNAME = "testUser";

    @BeforeEach
    void cleanUp() {
        travellerRepository.deleteAll();
    }

    @Test
    void testRegisterUserWhenUserIsValidThenResultIsUserSavedInDB () throws Exception {
        CreateUserDTO registerRequest = new CreateUserDTO(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);

        mockMvc.perform(post("/api/traveller/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        assertThat(travellerRepository.findByEmail("test@example.com")).isPresent();
    }


    @Test
    void testLoginUserWithValidCredentialsReturnsJwt() throws Exception {
        createTestUser();

        UserDTO loginRequest = new UserDTO(TEST_EMAIL, TEST_PASSWORD);

        mockMvc.perform(post("/api/traveller/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.username").value(TEST_EMAIL))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void testLoginUserWithInvalidCredentialsReturnsUnauthorized() throws Exception {
        createTestUser();

        UserDTO invalidRequest = new UserDTO(TEST_EMAIL, "wrongPassword");

        mockMvc.perform(post("/api/traveller/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccessProtectedEndpointWithValidToken() throws Exception {
        createTestUser();
        String token = authenticateAndGetToken();

        mockMvc.perform(get("/api/trips/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testAccessProtectedEndpointWithoutTokenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/trips/"))
                .andExpect(status().isUnauthorized());
    }

    private void createTestUser() throws Exception {
        CreateUserDTO registerRequest = new CreateUserDTO(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);

        mockMvc.perform(post("/api/traveller/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));
    }

    private String authenticateAndGetToken() throws Exception {
        UserDTO loginRequest = new UserDTO(TEST_EMAIL, TEST_PASSWORD);

        MvcResult result = mockMvc.perform(post("/api/traveller/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        return objectMapper.readValue(response, JwtDTO.class).token();
    }
}

