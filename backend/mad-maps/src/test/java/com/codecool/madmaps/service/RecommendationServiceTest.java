package com.codecool.madmaps.service;

import com.codecool.madmaps.DTO.Recommendation.RecommendationDTO;
import com.codecool.madmaps.DTO.Recommendation.RecommendationResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(recommendationService, "googleMapsApiKey", "test-api-key");
    }

    @Test
    void getRecommendations_SuccessfulResponse_ReturnsListOfDTOs() {
        // Arrange
        String location = "40.7128,-74.0060";
        String type = "restaurant";
        List<RecommendationDTO> mockResults = List.of(
                new RecommendationDTO("Cafe", "4.5", "2", "ChIJ12345"),
                new RecommendationDTO("Park", "4.8", "1", "ChIJ67890")
        );
        RecommendationResultDTO mockResponse = new RecommendationResultDTO(mockResults);

        when(restTemplate.getForObject(anyString(), eq(RecommendationResultDTO.class)))
                .thenReturn(mockResponse);

        List<RecommendationDTO> result = recommendationService.getRecommendations(location, type);

        assertEquals(2, result.size());
        RecommendationDTO firstResult = result.get(0);
        assertEquals("Cafe", firstResult.name());
        assertEquals("4.5", firstResult.rating());
        assertEquals("2", firstResult.price_level());
        assertEquals("ChIJ12345", firstResult.place_id());

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        verify(restTemplate).getForObject(urlCaptor.capture(), eq(RecommendationResultDTO.class));
        String expectedUrl = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=500&type=%s&rankby=prominence&key=test-api-key",
                location, type
        );
        assertEquals(expectedUrl, urlCaptor.getValue());
    }

    @Test
    void getRecommendations_EmptyResults_ReturnsEmptyList() {
        String location = "0,0";
        String type = "museum";
        RecommendationResultDTO mockResponse = new RecommendationResultDTO(Collections.emptyList());

        when(restTemplate.getForObject(anyString(), eq(RecommendationResultDTO.class)))
                .thenReturn(mockResponse);

        List<RecommendationDTO> result = recommendationService.getRecommendations(location, type);

        assertTrue(result.isEmpty());
    }

    @Test
    void getRecommendations_ApiCallFails_ThrowsRestClientException() {
        String location = "invalid-location";
        String type = "store";

        when(restTemplate.getForObject(anyString(), eq(RecommendationResultDTO.class)))
                .thenThrow(new RestClientException("API Error"));

        assertThrows(RestClientException.class, () ->
                recommendationService.getRecommendations(location, type)
        );
    }

    @Test
    void getRecommendations_AllFieldsMappedCorrectly() {
        String location = "34.0522,-118.2437";
        String type = "cafe";
        RecommendationDTO mockDto = new RecommendationDTO(
                "Blue Bottle Coffee",
                "4.7",
                "3",
                "ChIJD7fiBn8r3IARQJsbijM0wEQ"
        );
        RecommendationResultDTO mockResponse = new RecommendationResultDTO(List.of(mockDto));

        when(restTemplate.getForObject(anyString(), eq(RecommendationResultDTO.class)))
                .thenReturn(mockResponse);

        List<RecommendationDTO> result = recommendationService.getRecommendations(location, type);

        assertEquals(1, result.size());
        RecommendationDTO dto = result.get(0);
        assertAll(
                () -> assertEquals("Blue Bottle Coffee", dto.name()),
                () -> assertEquals("4.7", dto.rating()),
                () -> assertEquals("3", dto.price_level()),
                () -> assertEquals("ChIJD7fiBn8r3IARQJsbijM0wEQ", dto.place_id())
        );
    }
}