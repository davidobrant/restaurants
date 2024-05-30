package org.example.restaurants.services;

import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(UUID id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) throws NotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurant.getId())
                .orElse(null);

        if (existingRestaurant == null) return null;

        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setDescription(restaurant.getDescription());
        existingRestaurant.setRating(restaurant.getRating());
        existingRestaurant.setCity(restaurant.getCity());
        existingRestaurant.setStreet(restaurant.getStreet());
        existingRestaurant.setZip(restaurant.getZip());
        existingRestaurant.setPhone(restaurant.getPhone());
        existingRestaurant.setEmail(restaurant.getEmail());
        existingRestaurant.setMenu(restaurant.getMenu());

        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurantById(UUID id) {

    }

}
