package org.example.restaurants.repositories;

import org.example.restaurants.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RestaurantsRepository extends CrudRepository<Restaurant, UUID> {

    @Override
    List<Restaurant> findAll();
}
