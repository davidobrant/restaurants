package org.example.restaurants.repositories;

import org.example.restaurants.entities.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByCityContainingIgnoreCase(String city, Sort sort);

    List<Restaurant> findByRating(Integer rating, Sort sort);

    List<Restaurant> findByIsOpenTrue(Sort sort);

    List<Restaurant> findByIsOpenFalse(Sort sort);

    List<Restaurant> findByCityContainingIgnoreCaseAndIsOpenTrue(String city, Sort sort);

    List<Restaurant> findByCityContainingIgnoreCaseAndIsOpenFalse(String city, Sort sort);


}
