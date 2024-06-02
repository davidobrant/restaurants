package org.example.restaurants.services;

import org.example.restaurants.entities.OpeningHour;
import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.repositories.OpeningHourRepository;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    OpeningHourRepository openingHourRepository;


    public Page<Restaurant> getAllRestaurants(String name, String city, Boolean isOpen, String sortBy, String sortDir, int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<Restaurant> filteredRestaurants = restaurantRepository.findAll(sort);

        if (isOpen != null) {
            filteredRestaurants = filteredRestaurants.stream()
                    .filter(restaurant -> restaurant.isOpen() == isOpen)
                    .collect(Collectors.toList());
        }

        if (name != null && !name.isEmpty()) {
            filteredRestaurants = filteredRestaurants.stream()
                    .filter(restaurant -> restaurant.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (city != null && !city.isEmpty()) {
            filteredRestaurants = filteredRestaurants.stream()
                    .filter(restaurant -> restaurant.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }

        if ("rating".equals(sortBy)) {
            if ("asc".equalsIgnoreCase(sortDir)) {
                filteredRestaurants.sort(Comparator.comparingDouble(Restaurant::getRating));
            } else {
                filteredRestaurants.sort(Comparator.comparingDouble(Restaurant::getRating).reversed());
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredRestaurants.size());
        List<Restaurant> paginatedRestaurants = filteredRestaurants.subList(start, end);

        return new PageImpl<>(paginatedRestaurants, pageable, filteredRestaurants.size());
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

    public List<OpeningHour> getOpeningHoursForRestaurant(Long restaurantId) {
        return openingHourRepository.findByRestaurantId(restaurantId);
    }

    public OpeningHour saveOpeningHour(OpeningHour openingHour) {
        return openingHourRepository.save(openingHour);
    }
}
