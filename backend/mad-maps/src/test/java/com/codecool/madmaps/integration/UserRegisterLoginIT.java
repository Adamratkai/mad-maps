package com.codecool.madmaps.integration;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class UserRegisterLoginIT {

    @BeforeAll
    static void loadEnvVariables() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("API_KEY", dotenv.get("API_KEY"));
    }

}
