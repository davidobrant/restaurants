package org.example.restaurants;
import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestGetRestaurantByIdIfValid() {
        long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        try {
            Restaurant found = restaurantService.getRestaurantById(id);
            assertNotNull(found);
            assertEquals(id, found.getId());
            System.out.println("Found restaurant successfully");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed with exception " + e.getMessage());
        }
    }

//    @Test
//    void TestGetRestaurantByIdIfInvalid() {
//        long id = 1L;
//        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
//
//        try {
//            restaurantService.getRestaurantById(id);
//            fail("testGetRestaurantByIdInvalid: Expected NotFoundException but none was thrown");
//        } catch (NotFoundException e) {
//            System.out.println("Success - NotFoundException thrown as expected");
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed with unexpected exception " + e.getMessage());
//        }
//    }

    @Test
    void testAddRestaurant() {
        Restaurant restaurant = new Restaurant();
        double rating = 3;
        restaurant.setName("O'Learys");
        restaurant.setDescription("Sportsbar");
        restaurant.setCity("Uppsala");
//        restaurant.setOpen(true);
        restaurant.setRating(rating);
        restaurant.setPhone("0705345234");
        restaurant.setStreet("Dragarbrunnsgata 45");
        restaurant.setZip("72460");
        restaurant.setEmail("donotemailus@youwillbeignored.com");

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        try {
            Restaurant created = restaurantService.saveRestaurant(restaurant);
            assertNotNull(created);
            assertEquals("O'Learys", created.getName());
            assertEquals("Sportsbar", created.getDescription());
            assertEquals("Uppsala", created.getCity());
//            assertEquals(true, created.isOpen());
            assertEquals(rating, created.getRating());
            assertEquals("0705345234", created.getPhone());
            assertEquals("Dragarbrunnsgata 45", created.getStreet());
            assertEquals("72460", created.getZip());
            assertEquals("donotemailus@youwillbeignored.com", created.getEmail());
            System.out.println("Restaurant added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed with exception " + e.getMessage());
        }

    }

//    @Test
//    void updateRestaurantTest() {
//        long id = 1L;
//        Restaurant existingRestaurant = new Restaurant();
//        existingRestaurant.setId(id);
//        when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));
//
//        Restaurant updateData = new Restaurant();
//        updateData.setName("Updated Name");
//        updateData.setCity("Updated City");
//
//        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updateData);
//        try {
//            Restaurant updated = restaurantService.updateRestaurant(updateData);
//            assertNotNull(updated);
//            assertEquals("Updated Name", updated.getName());
//            assertEquals("Updated City", updated.getCity());
//            System.out.println("Update Restaurant: Success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed with exception " + e.getMessage());
//        }
//    }

    @Test
    void testDeleteRestaurant() {
        long id = 1L;
        doNothing().when(restaurantRepository).deleteById(id);

        try {
            restaurantService.deleteRestaurantById(id);
            verify(restaurantRepository, times(1)).deleteById(id);
            System.out.println("Deleted succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Deleting failed with exception " + e.getMessage());
        }
    }

    @Test
    void testDeleteAllRestaurants(){
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("Restaurant 1");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("Restaurant 2");
        System.out.println("Restaurants Ids are : " + restaurant1 + restaurant2);
        doNothing().when(restaurantRepository).deleteAll();
        try {
            restaurantService.deleteAllRestaurants();
            verify(restaurantRepository, times(1)).deleteAll();
            System.out.println("Deleted succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Deleting failed with exception " + e.getMessage());
        }
    }
    @Test
    void testGetAllCities() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setCity("Gothenburg");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setCity("Stockholm");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setCity("Uppsala");

        List<Restaurant> mockRestaurants = List.of(restaurant1, restaurant2, restaurant3);
        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        System.out.println("Cities are: " + mockRestaurants);
        try {
            Set<String> cities = restaurantService.getAllCities();
            assertNotNull(cities);
            assertEquals(3, cities.size());
            assertTrue(cities.contains("Gothenburg"));
            assertTrue(cities.contains("Stockholm"));
            assertTrue(cities.contains("Uppsala"));
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed with exception " + e.getMessage());
        }
    }

  // All restaurants is eventually the same code but restaurants instead of cities
}