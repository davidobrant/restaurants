package org.example.restaurants.controllers;

import org.example.restaurants.entities.Menu;
import org.example.restaurants.entities.OpeningHour;
import org.example.restaurants.entities.Restaurant;
import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.services.MenuService;
import org.example.restaurants.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    MenuService menuService;

    @GetMapping
    public String getRestaurantsPage(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) Boolean isOpen,
                                     @RequestParam(required = false, defaultValue = "name") String sortBy,
                                     @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                     @RequestParam(defaultValue = "0") Integer pageNumber,
                                     @RequestParam(defaultValue = "20") Integer pageSize,
                                     Model model) {

        Page<Restaurant> restaurantsByPage = restaurantService.getAllRestaurants(name, city, isOpen, sortBy, sortDir, pageNumber, pageSize);

        model.addAttribute("activePage", "restaurants");
        model.addAttribute("restaurants", restaurantsByPage.getContent());
        model.addAttribute("page", restaurantsByPage);
        model.addAttribute("cities", restaurantService.getAllCities());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "restaurants";
    }


    @GetMapping("{id}")
    public String getRestaurantPage(@PathVariable("id") Long id, Model model) {
        try {
            var restaurant = restaurantService.getRestaurantById(id);
            if (restaurant == null) {
                throw new NotFoundException("Restaurant not found...");
            }

            Menu menu = menuService.getMenuByRestaurant(restaurant);
            List<OpeningHour> openingHours = restaurantService.getOpeningHoursForRestaurant(id);

            model.addAttribute("restaurant", restaurant);
            model.addAttribute("menu", menu);
            model.addAttribute("openingHours", openingHours);
            model.addAttribute("title", restaurant.getName() + " | Restaurant Details");

            return "restaurant";
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Restaurant not found..." + "\n(Invalid ID)");
        }
    }

}
