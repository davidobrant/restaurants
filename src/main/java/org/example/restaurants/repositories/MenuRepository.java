package org.example.restaurants.repositories;

import org.example.restaurants.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByRestaurantId(Long restaurantId);

}