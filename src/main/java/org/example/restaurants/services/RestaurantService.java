package org.example.restaurants.services;

import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants(String name, String city, Boolean isOpen, Integer rating, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        if ("rating".equalsIgnoreCase(sortBy)) {
            if ("asc".equalsIgnoreCase(sortDir)) {
                sort = sort.and(Sort.by(Sort.Direction.ASC, "rating"));
            } else if ("desc".equalsIgnoreCase(sortDir)) {
                sort = sort.and(Sort.by(Sort.Direction.DESC, "rating"));
            }
        }

        if (name != null && !name.isEmpty()) {
            return restaurantRepository.findByNameContainingIgnoreCase(name);
        } else if (city != null && !city.isEmpty() && isOpen != null) {
            if (isOpen) {
                return restaurantRepository.findByCityContainingIgnoreCaseAndIsOpenTrue(city, sort);
            } else {
                return restaurantRepository.findByCityContainingIgnoreCaseAndIsOpenFalse(city, sort);
            }
        } else if (city != null && !city.isEmpty()) {
            return restaurantRepository.findByCityContainingIgnoreCase(city, sort);
        } else if (isOpen != null) {
            if (isOpen) {
                return restaurantRepository.findByIsOpenTrue(sort);
            } else {
                return restaurantRepository.findByIsOpenFalse(sort);
            }
        } else if (rating != null) {
            return restaurantRepository.findByRating(rating, sort);
        } else {
            Pageable pageable = PageRequest.of(0, 100, sort);
            return restaurantRepository.findAll(pageable).getContent();
        }
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        var existing = restaurantRepository.findById(restaurant.getId()).orElse(null);
        if (existing == null) return null;
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
    }

    public void deleteAllRestaurants() {
        restaurantRepository.deleteAll();
    }

    public Set<String> getAllCities() {
        return restaurantRepository.findAll()
                .stream()
                .map(Restaurant::getCity)
                .collect(Collectors.toSet());
    }
}
