package com.codecool.madmaps.service;

import com.codecool.madmaps.DTO.Trip.*;
import com.codecool.madmaps.model.Photo.Photo;
import com.codecool.madmaps.model.Place.Place;
import com.codecool.madmaps.model.Traveler.Traveller;
import com.codecool.madmaps.model.Trip.Trip;
import com.codecool.madmaps.model.TripActivity.TripActivity;
import com.codecool.madmaps.repository.PlaceRepository;
import com.codecool.madmaps.repository.TripActivityRepository;
import com.codecool.madmaps.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private TripActivityRepository tripActivityRepository;
    @Mock
    private TravellerService travellerService;

    @InjectMocks
    private TripService tripService;

    private Traveller traveller;
    private Trip trip;
    private Place place;
    private TripActivity tripActivity;
    private Photo photo;
    private UUID tripId;

    @BeforeEach
    void setUp() {
        traveller = new Traveller();
        traveller.setId(1L);
        traveller.setEmail("testUser@test.com");
        tripActivity = new TripActivity();
        trip = new Trip();
        tripId = UUID.randomUUID();
        place = new Place();
        trip.setName("Test Trip");
        trip.setTripId(tripId);
        trip.setStartDate(LocalDate.now());
        trip.setEndDate(LocalDate.now().plusDays(5));
        trip.setTraveller(traveller);
        trip.setTripActivities(List.of(tripActivity));
        tripActivity.setTrip(trip);
        tripActivity.setPlace(place);
        tripActivity.setVisitTime(LocalDateTime.now());
        place.setPlaceId("123");
        place.setName("Test Place");
        place.setRating(4.5);
        place.setPriceLevel(2);
        place.setOpeningHours(List.of("Mon 9-5"));photo = new Photo();
        place.setPhotos(List.of(photo));
    }

    @Test
    void createTripWhenGetsTripCreatesTripAndReturnsTripId() {
        TripCreateDTO tripCreateDTO = new TripCreateDTO("Test Trip", LocalDate.now(), LocalDate.now().plusDays(5));
        when(travellerService.getAuthenticatedUser()).thenReturn(traveller);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        UUID createdTripId = tripService.createTrip(tripCreateDTO);
        assertNotNull(createdTripId);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void addTripActivityWhenGetsTripActivityAddsItAndReturnsActivityId() {
        UUID activityId = UUID.randomUUID();
        tripActivity.setTripActivityId(activityId);
        when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(trip));
        when(placeRepository.findByPlaceId(place.getPlaceId())).thenReturn(Optional.of(place));
        when(tripActivityRepository.save(any(TripActivity.class))).thenReturn(tripActivity);
        TripActivityCreateDTO dto = new TripActivityCreateDTO(place.getPlaceId(), LocalDateTime.now());
        UUID returnedId = tripService.addTripActivity(tripId, dto);
        assertEquals(activityId, returnedId);
        verify(tripRepository).findByTripId(tripId);
        verify(placeRepository).findByPlaceId(place.getPlaceId());
        verify(tripActivityRepository).save(any(TripActivity.class));
    }

    @Test
    void deleteTripActivityByIdWhenGetsActivityIdDeletesItAndReturnsDeleteCount() {
        UUID activityId = UUID.randomUUID();
        when(tripActivityRepository.deleteByTripActivityId(activityId)).thenReturn(1);
        int deleted = tripService.deleteTripActivityById(activityId);
        assertEquals(1, deleted);
        verify(tripActivityRepository).deleteByTripActivityId(activityId);
    }

    @Test
    void getTripsReturnsListOfAllTripsWithoutActivities() {
        when(tripRepository.findAll()).thenReturn(List.of(trip));
        List<TripDTO> trips = tripService.getTrips();
        assertEquals(1, trips.size());
    }

    @Test
    void getTripByIdWhenGetsATripReturnsTripWithActivities() {
        when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(trip));
        TripDetailsDTO tripDetailsDTO = tripService.getTripById(tripId);
        assertNotNull(tripDetailsDTO);
    }

    @Test
    void deleteTripByIdWhenGetsTripIdDeletesTripAndReturnsDeleteCount() {
        when(tripRepository.deleteByTripId(tripId)).thenReturn(1);
        int deleted = tripService.deleteTripById(tripId);
        assertEquals(1, deleted);
    }

    @Test
    void updateTripByIdWhenGetsTripsUpdatesTrip() {
        TripUpdateDTO tripUpdateDTO = new TripUpdateDTO("Updated Trip", LocalDate.now(), LocalDate.now().plusDays(3));
        when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(trip));
        tripService.updateTripById(tripId, tripUpdateDTO);
        assertEquals("Updated Trip", trip.getName());
        verify(tripRepository).findByTripId(tripId);
    }

    @Test
    void getTripsByTravellerWhenGetsTravellerReturnsTripsOfTraveller() {
        when(travellerService.getAuthenticatedUser()).thenReturn(traveller);
        when(tripRepository.findByTraveller(traveller)).thenReturn(List.of(trip));
        List<TripDTO> trips = tripService.getTripsByTraveller();
        assertEquals(1, trips.size());
    }
}
