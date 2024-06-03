package org.example.restaurants.repositories;

import org.example.restaurants.entities.MenuSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuSectionRepository extends JpaRepository<MenuSection, Long> {
    List<MenuSection> findByMenuId(Long menuId);
}
