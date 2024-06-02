package org.example.restaurants.repositories;

import org.example.restaurants.entities.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {

    List<OpeningHour> findByRestaurantId(Long restaurantId);

}
