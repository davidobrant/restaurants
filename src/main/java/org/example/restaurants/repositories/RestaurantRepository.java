package org.example.restaurants.repositories;

import org.example.restaurants.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Restaurant> findByCityContainingIgnoreCase(String city, Pageable pageable);

    Page<Restaurant> findByRating(Integer rating, Pageable pageable);

    Page<Restaurant> findByIsOpenTrue(Pageable pageable);

    Page<Restaurant> findByIsOpenFalse(Pageable pageable);

    Page<Restaurant> findByCityContainingIgnoreCaseAndIsOpenTrue(String city, Pageable pageable);

    Page<Restaurant> findByCityContainingIgnoreCaseAndIsOpenFalse(String city, Pageable pageable);


}
