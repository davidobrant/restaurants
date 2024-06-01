package org.example.restaurants.db;

import org.example.restaurants.repositories.MenuRepository;
import org.example.restaurants.repositories.RestaurantRepository;
import org.example.restaurants.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Init implements CommandLineRunner {

    private Random random;
    private Generator generator;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    public Init() {
        this.generator = new Generator();
    }

    @Override
    public void run(String... args) {
        generateDummyData();
    }

    private void generateDummyData() {
        if (restaurantRepository.count() > 50) return;

        restaurantRepository.saveAll(generator.generateRestaurants(100));

    }


}
