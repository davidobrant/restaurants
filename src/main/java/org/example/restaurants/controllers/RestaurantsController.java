package org.example.restaurants.controllers;

import org.example.restaurants.exceptions.NotFoundException;
import org.example.restaurants.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/restaurants")
public class RestaurantsController {


    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public String getRestaurantsPage(Model model) {

        model.addAttribute("activePage", "restaurants");

        return "restaurants";
    }

    @GetMapping("{id}")
    public String getRestaurantPage(@PathVariable("id") String id, Model model) {
        try {
            UUID uuid = UUID.fromString(id);
            var restaurant = restaurantService.getRestaurantById(uuid);
            if (restaurant == null) {
                throw new NotFoundException("Restaurant not found...");
            }
            model.addAttribute("restaurant", restaurant);
            return "restaurant";
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Restaurant not found..." + "\n(Invalid ID...)");
        }
    }

}
