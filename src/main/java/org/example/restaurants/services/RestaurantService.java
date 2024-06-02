package org.example.restaurants.services;

import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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


    public Page<Restaurant> getAllRestaurants(String name, String city, Boolean isOpen, Integer rating, String sortBy, String sortDir, int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        if ("rating".equalsIgnoreCase(sortBy)) {
            if ("asc".equalsIgnoreCase(sortDir)) {
                sort = sort.and(Sort.by(Sort.Direction.ASC, "rating"));
            } else if ("desc".equalsIgnoreCase(sortDir)) {
                sort = sort.and(Sort.by(Sort.Direction.DESC, "rating"));
            }
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (name != null && !name.isEmpty()) {
            return restaurantRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (city != null && !city.isEmpty() && isOpen != null) {
            if (isOpen) {
                return restaurantRepository.findByCityContainingIgnoreCaseAndIsOpenTrue(city, pageable);
            } else {
                return restaurantRepository.findByCityContainingIgnoreCaseAndIsOpenFalse(city, pageable);
            }
        } else if (city != null && !city.isEmpty()) {
            return restaurantRepository.findByCityContainingIgnoreCase(city, pageable);
        } else if (isOpen != null) {
            if (isOpen) {
                return restaurantRepository.findByIsOpenTrue(pageable);
            } else {
                return restaurantRepository.findByIsOpenFalse(pageable);
            }
        } else if (rating != null) {
            return restaurantRepository.findByRating(rating, pageable);
        } else {
            return restaurantRepository.findAll(pageable);
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
