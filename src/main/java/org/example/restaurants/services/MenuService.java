package org.example.restaurants.services;

import org.example.restaurants.entities.Menu;
import org.example.restaurants.entities.MenuItem;
import org.example.restaurants.entities.MenuSection;
import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.repositories.MenuItemRepository;
import org.example.restaurants.repositories.MenuRepository;
import org.example.restaurants.repositories.MenuSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuSectionRepository menuSectionRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    public Menu getMenuByRestaurant(Restaurant restaurant) {
        return menuRepository.findByRestaurantId(restaurant.getId()).orElse(null);
    }

    public Menu addMenuToRestaurant(Menu menu, Restaurant restaurant) {
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    public MenuSection addSectionToMenu(MenuSection section, Menu menu) {
        section.setMenu(menu);
        return menuSectionRepository.save(section);
    }

    public MenuItem addItemToSection(MenuItem item, MenuSection section) {
        item.setSection(section);
        return menuItemRepository.save(item);
    }

    public List<MenuSection> getSectionsByMenu(Menu menu) {
        return menuSectionRepository.findByMenuId(menu.getId());
    }

    public List<MenuItem> getItemsBySection(MenuSection section) {
        return menuItemRepository.findBySectionId(section.getId());
    }

    public void deleteMenu(Menu menu) {
        menuRepository.delete(menu);
    }

}
