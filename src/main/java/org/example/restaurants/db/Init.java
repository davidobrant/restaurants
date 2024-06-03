package org.example.restaurants.db;

import org.example.restaurants.repositories.MenuItemRepository;
import org.example.restaurants.repositories.MenuRepository;
import org.example.restaurants.repositories.MenuSectionRepository;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Init implements CommandLineRunner {

    private Generator generator;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuSectionRepository menuSectionRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    public Init() {
        this.generator = new Generator();
    }

    @Override
    public void run(String... args) {
        generateDummyData();
    }

    private void generateDummyData() {

//        clearDB();

        if (restaurantRepository.count() > 50) return;

        var restaurants = generator.generateRestaurants(100);

        for (var restaurant : restaurants) {
            restaurantRepository.save(restaurant);

            var menu = restaurant.getMenu();
            if (menu != null) {
                menu.setRestaurant(restaurant);
                menuRepository.save(menu);

                for (var section : menu.getSections()) {
                    section.setMenu(menu);
                    menuSectionRepository.save(section);

                    for (var item : section.getItems()) {
                        item.setSection(section);
                        menuItemRepository.save(item);
                    }
                }
            }
        }
    }


    private void clearDB() {
        restaurantRepository.deleteAll();
        menuRepository.deleteAll();
        menuSectionRepository.deleteAll();
        menuItemRepository.deleteAll();
    }


}
