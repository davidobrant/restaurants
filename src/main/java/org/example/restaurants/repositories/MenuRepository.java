package org.example.restaurants.repositories;

import org.example.restaurants.entities.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenuRepository extends CrudRepository<Menu, Long> {
}
