package org.example.restaurants.repositories;

import org.example.restaurants.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findBySectionId(Long sectionId);
}
