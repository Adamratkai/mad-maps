package com.codecool.madmaps.service;

import com.codecool.madmaps.DTO.Place.PlaceCreateDTO;
import com.codecool.madmaps.DTO.Place.PlaceDTO;
import com.codecool.madmaps.DTO.Recommendation.DetailedPlaceDTO;
import com.codecool.madmaps.DTO.Recommendation.DetailedPlaceResultDTO;
import com.codecool.madmaps.DTO.Recommendation.PlaceOpeningHours;
import com.codecool.madmaps.DTO.Recommendation.PlacePhotoDTO;
import com.codecool.madmaps.model.Photo.Photo;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.PlaceType.PlaceType;
import com.codecool.madmaps.repository.PhotoRepository;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.PlaceTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceTypeRepository placeTypeRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(placeService, "googleMapsApiKey", "test-key");
    }

    @Test
    void getAllPlacesWhenHappyCaseItReturnsListOfDTOs() {
        Place place = new Place();
        place.setPlaceId("123");
        place.setName("Test Place");
        place.setRating(4.5);
        place.setPriceLevel(2);
        place.setOpeningHours(List.of("Mon 9-5"));
        Photo photo = new Photo();
        photo.setPhotoId(UUID.randomUUID());
        place.setPhotos(List.of(photo));
        when(placeRepository.findAll()).thenReturn(List.of(place));

        List<PlaceDTO> result = placeService.getAllPlaces();

        assertThat(result).hasSize(1);
        PlaceDTO dto = result.get(0);
        assertThat(dto.placeId()).isEqualTo("123");
        assertThat(dto.name()).isEqualTo("Test Place");
        assertThat(dto.rating()).isEqualTo(4.5);
        assertThat(dto.priceLevel()).isEqualTo(2);
        assertThat(dto.openingHours()).isEqualTo(List.of("Mon 9-5"));
        assertThat(dto.photos()).containsExactly(photo.getPhotoId());
    }

    @Test
    void getAllPlacesWhenRepositoryThrowsExceptionItPropagatesException() {
        when(placeRepository.findAll()).thenThrow(new DataAccessException("Database error") {});

        assertThrows(DataAccessException.class, () -> placeService.getAllPlaces());
    }

    @Test
    void getPlaceByIdWhenHappyCaseItPlaceExistsInRepoThenReturnsDTO() {
        String placeId = "123";
        Place place = new Place();
        place.setPlaceId(placeId);
        place.setName("Test Place");
        place.setRating(4.5);
        place.setPriceLevel(2);
        place.setOpeningHours(List.of("Mon 9-5"));
        Photo photo = new Photo();
        photo.setPhotoId(UUID.randomUUID());
        place.setPhotos(List.of(photo));
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.of(place));

        PlaceDTO result = placeService.getPlaceById(placeId);

        assertThat(result.placeId()).isEqualTo(placeId);
        assertThat(result.name()).isEqualTo("Test Place");
        assertThat(result.rating()).isEqualTo(4.5);
        assertThat(result.priceLevel()).isEqualTo(2);
        assertThat(result.openingHours()).isEqualTo(List.of("Mon 9-5"));
        assertThat(result.photos()).containsExactly(photo.getPhotoId());
    }

    @Test
    void getPlaceByIdWhenGoogleApiFailsItThrowsException() {
        String placeId = "123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        String fields = "name,place_id,rating,price_level,photos,opening_hours/weekday_text,types";
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?fields=%s&place_id=%s&key=%s",
                fields, placeId, "test-key");
        when(restTemplate.getForObject(eq(url), eq(DetailedPlaceResultDTO.class)))
                .thenThrow(new RestClientException("API error"));

        assertThrows(RestClientException.class, () -> placeService.getPlaceById(placeId));
    }

    @Test
    void createAndGetPlaceDTOWhenHappyCaseItReturnsDTO() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1", "type2"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of("ref1", "ref2", "ref3")
        );
        Place savedPlace = new Place();
        savedPlace.setPlaceId("place123");
        savedPlace.setName("Test Place");
        savedPlace.setRating(4.5);
        savedPlace.setPriceLevel(2);
        savedPlace.setOpeningHours(List.of("Mon 9-5"));
        when(placeRepository.save(any(Place.class))).thenReturn(savedPlace);
        when(restTemplate.getForObject(anyString(), eq(byte[].class))).thenReturn(new byte[0]);
        when(photoRepository.save(any(Photo.class))).thenAnswer(invocation -> {
            Photo photo = invocation.getArgument(0);
            photo.setPhotoId(UUID.randomUUID());
            return photo;
        });

        PlaceDTO result = placeService.createAndGetPlaceDTO(dto);

        assertThat(result.placeId()).isEqualTo("place123");
        assertThat(result.name()).isEqualTo("Test Place");
        assertThat(result.rating()).isEqualTo(4.5);
        assertThat(result.priceLevel()).isEqualTo(2);
        assertThat(result.openingHours()).isEqualTo(List.of("Mon 9-5"));
        assertThat(result.photos()).hasSize(2);
        verify(photoRepository, times(2)).save(any(Photo.class));
    }

    @Test
    void createAndGetPlaceDTOWhenSavePlaceFailsItThrowsException() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of("ref1")
        );
        when(placeRepository.save(any(Place.class))).thenThrow(new DataAccessException("DB error") {});

        assertThrows(DataAccessException.class, () -> placeService.createAndGetPlaceDTO(dto));
    }

    @Test
    void findOrCreatePlaceTypeWhenTypeExistsItReturnsExistingType() {
        String type = "restaurant";
        PlaceType existingType = new PlaceType();
        existingType.setPlaceType(type);
        when(placeTypeRepository.findByPlaceType(type)).thenReturn(Optional.of(existingType));

        PlaceType result = placeService.findOrCreatePlaceType(type);

        assertThat(result).isEqualTo(existingType);
        verify(placeTypeRepository, never()).save(any());
    }

    @Test
    void findOrCreatePlaceTypeWhenSaveFailsItThrowsException() {
        String type = "cafe";
        when(placeTypeRepository.findByPlaceType(type)).thenReturn(Optional.empty());
        when(placeTypeRepository.save(any(PlaceType.class))).thenThrow(new DataAccessException("DB error") {});

        assertThrows(DataAccessException.class, () -> placeService.findOrCreatePlaceType(type));
    }

    @Test
    void getPhotoByIdWhenHappyCaseItReturnsPhotoBytes() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new Photo();
        photo.setPhotoId(photoId);
        byte[] expectedBytes = new byte[]{0x1, 0x2};
        photo.setPhoto(expectedBytes);
        when(photoRepository.findByPhotoId(photoId)).thenReturn(Optional.of(photo));

        byte[] result = placeService.getPhotoById(photoId);

        assertThat(result).isEqualTo(expectedBytes);
    }

    @Test
    void getPhotoByIdWhenPhotoNotFoundItThrowsException() {
        UUID photoId = UUID.randomUUID();
        when(photoRepository.findByPhotoId(photoId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> placeService.getPhotoById(photoId));
    }

    @Test
    void getPhotoIdsByPlaceIdWhenHappyCaseItReturnsPhotoIds() {
        String placeId = "place123";
        Place place = new Place();
        Photo photo1 = new Photo();
        photo1.setPhotoId(UUID.randomUUID());
        Photo photo2 = new Photo();
        photo2.setPhotoId(UUID.randomUUID());
        place.setPhotos(List.of(photo1, photo2));
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.of(place));

        List<UUID> result = placeService.getPhotoIdsByPlaceId(placeId);

        assertThat(result).containsExactly(photo1.getPhotoId(), photo2.getPhotoId());
    }

    @Test
    void getPhotoIdsByPlaceIdWhenPlaceNotFoundItThrowsException() {
        String placeId = "invalid123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> placeService.getPhotoIdsByPlaceId(placeId));
    }


    @Test
    void createPhotosWhenTwoReferencesItSavesTwoPhotos() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of("ref1", "ref2")
        );
        Place savedPlace = new Place();
        when(placeRepository.save(any())).thenReturn(savedPlace);
        when(restTemplate.getForObject(anyString(), eq(byte[].class))).thenReturn(new byte[0]);

        PlaceDTO result = placeService.createAndGetPlaceDTO(dto);

        assertThat(result.photos()).hasSize(2);
        verify(photoRepository, times(2)).save(any());
    }

    @Test
    void createPhotosWhenEmptyReferencesItReturnsEmptyList() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of()
        );
        Place savedPlace = new Place();
        when(placeRepository.save(any())).thenReturn(savedPlace);

        PlaceDTO result = placeService.createAndGetPlaceDTO(dto);

        assertThat(result.photos()).isEmpty();
        verify(photoRepository, never()).save(any());
    }

    @Test
    void createPhotosWhenPhotoFetchFailsItPropagatesException() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of("ref1")
        );
        when(restTemplate.getForObject(anyString(), eq(byte[].class)))
                .thenThrow(new RestClientException("Photo fetch failed"));

        assertThrows(RestClientException.class, () -> placeService.createAndGetPlaceDTO(dto));
    }

    @Test
    void findOrCreatePlaceWhenApiReturnsEmptyPhotosThenNoPhotosSaved() {
        String placeId = "123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        DetailedPlaceDTO detailedPlace = new DetailedPlaceDTO(
                "Test Place",
                4.5,
                2,
                "place123",
                List.of(),
                List.of("type1"),
                new PlaceOpeningHours(List.of("Mon 9-5"))
        );
        DetailedPlaceResultDTO response = new DetailedPlaceResultDTO(detailedPlace);
        when(restTemplate.getForObject(anyString(), eq(DetailedPlaceResultDTO.class)))
                .thenReturn(response);

        Place savedPlace = new Place();
        when(placeRepository.save(any(Place.class))).thenReturn(savedPlace);

        Place result = placeService.findOrCreatePlace(placeId);
        assertThat(result.getPhotos()).isEmpty();
    }

    @Test
    void findOrCreatePlaceWhenApiReturnsNullResultItThrowsException() {
        String placeId = "123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());
        DetailedPlaceResultDTO response = new DetailedPlaceResultDTO(null);
        when(restTemplate.getForObject(anyString(), eq(DetailedPlaceResultDTO.class)))
                .thenReturn(response);

        assertThrows(NullPointerException.class, () -> placeService.findOrCreatePlace(placeId));
    }

    @Test
    void findOrCreatePlaceWhenNullOpeningHoursItThrowsException() {
        String placeId = "123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        DetailedPlaceDTO detailedPlace = new DetailedPlaceDTO(
                "Test Place",
                4.5,
                2,
                "place123",
                List.of(new PlacePhotoDTO("ref1", "100", "200")),
                List.of("type1"),
                null
        );
        DetailedPlaceResultDTO response = new DetailedPlaceResultDTO(detailedPlace);
        when(restTemplate.getForObject(anyString(), eq(DetailedPlaceResultDTO.class)))
                .thenReturn(response);

        assertThrows(NullPointerException.class, () -> placeService.findOrCreatePlace(placeId));
    }

    @Test
    void findOrCreatePlaceWhenNullTypesItThrowsException() {
        String placeId = "123";
        when(placeRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        DetailedPlaceDTO detailedPlace = new DetailedPlaceDTO(
                "Test Place",
                4.5,
                2,
                "place123",
                List.of(new PlacePhotoDTO("ref1", "100", "200")),
                null,
                new PlaceOpeningHours(List.of("Mon 9-5"))
        );
        DetailedPlaceResultDTO response = new DetailedPlaceResultDTO(detailedPlace);
        when(restTemplate.getForObject(anyString(), eq(DetailedPlaceResultDTO.class)))
                .thenReturn(response);

        assertThrows(NullPointerException.class, () -> placeService.findOrCreatePlace(placeId));
    }

    @Test
    void createPhotosWhenMoreThanTwoReferencesThenSavesOnlyTwo() {
        PlaceCreateDTO dto = new PlaceCreateDTO(
                "place123",
                "Test Place",
                List.of("type1"),
                4.5,
                2,
                List.of("Mon 9-5"),
                List.of("ref1", "ref2", "ref3")
        );
        Place savedPlace = new Place();
        when(placeRepository.save(any())).thenReturn(savedPlace);
        when(restTemplate.getForObject(anyString(), eq(byte[].class))).thenReturn(new byte[0]);

        PlaceDTO result = placeService.createAndGetPlaceDTO(dto);
        assertThat(result.photos()).hasSize(2);
        verify(photoRepository, times(2)).save(any());
    }
}